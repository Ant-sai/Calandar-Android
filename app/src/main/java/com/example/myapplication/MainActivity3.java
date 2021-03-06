package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    //Créations des différents éléments

    ContentValues val = new ContentValues();
    ListView lst;
    String arrNom [], arrDate [],  arrType[];

    int arrParti [];
    SQLiteDatabase db;
    Button btnRetour;
    ArrayList <String> arrDisplay;
    TextView msg;
    Button btnSend;
    final int SEND_SMS_PERMISSION = 1;
    EditText number;
    String message ="";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // A la création de la page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        arrDisplay = new ArrayList<String>();

        lst = (ListView) findViewById(R.id.listdata);  // Afiliation a la listeView
        btnRetour = (Button) findViewById(R.id.btn_retour);// Afiliation au Bouton
        msg = (TextView) findViewById(R.id.msg);
        btnSend = findViewById(R.id.send);
        number = findViewById(R.id.inputNumber);
        msg.setText(message);

        db = openOrCreateDatabase("Events3.db",SQLiteDatabase.CREATE_IF_NECESSARY, null); // Si La bdd Events.bd existe pas il créer une base de donnée.

        Cursor c = db.query("Event", null,null,null,null,null,null); // Curseur pour la Table

        // Initialisation des Tableau de la taille de la BDD
        arrNom = new String[c.getCount()];
        arrDate = new String[c.getCount()];
        arrParti = new int[c.getCount()];
        arrType = new String[c.getCount()];

        // PERMISSIONS MSG
        if(checkPermission(Manifest.permission.SEND_SMS)){
            btnSend.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION);
        }
        //
        //BOUTON ENVOYER
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = number.getText().toString();
                String smsMessage = message;
                if (phoneNumber == null || phoneNumber.length() == 0 || smsMessage == null || smsMessage.length() == 0){
                    return;
                }
                if (checkPermission(Manifest.permission.SEND_SMS)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber,null,smsMessage,null,null);
                    Toast.makeText(getApplicationContext(),"Message envoyé !",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Message pas envoyé ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //

        btnRetour.setOnClickListener(new View.OnClickListener() { // Bouton Retour
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this, MainActivity.class));
            }
        });

        c.moveToFirst(); // Initialisation du curseur au 1er élément
        for (int i=0 ; i< arrNom.length;i++) // Pour chaque élément de la taille d'un tableau ( tous identique)
        {
            // Chaque case des tableau va prendre la valeur d'une colonne on ce retourve avec 3 tableau qui on toutes les infos
            arrNom [i] = c.getString(1);
            arrDate [i] = c.getString(3);
            arrParti [i] = c.getInt(2);
            arrType [i] = c.getString(4);

            String partiString = Integer.toString(arrParti [i]); // on converti le nombre de participants en String

            arrDisplay.add("Evenement : " + arrNom[i] +" \n Date : "+ arrDate [i] + " \n Participants : " + partiString + " \n Type : " + arrType[i]); // Tableau qui va afficher TOUT les élements
            Toast.makeText(this,"Nom : "+ arrNom[i],1000).show();
            c.moveToNext(); // Prochaine Ligne du tableau
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrDisplay); // Méthode spécifique aux ListView qui demande 1 : Activity de la listView 2: Layout de la ListView 3: Un tableau avec TOUT les infos qu'il va afficher
        lst.setAdapter(adp); // on lie notre adapteur a notre liste

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() { // quand on appuie sur un item du listeneur affiche le titre de l'éèenement sur le quel on a cliqué
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),arrDisplay.get(position),1000).show();
                message = "Bonjour voici les informations de l'événement \n " + arrDisplay.get(position);
                msg.setText(message);
            }
        });

        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(MainActivity3.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle(" Etes vous sur ?")
                        .setMessage("Voulez vous supprimmer cet item ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrDisplay.remove(which_item);
                                adp.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Non",null).show();
                return true;
            }
        });
    }
    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}