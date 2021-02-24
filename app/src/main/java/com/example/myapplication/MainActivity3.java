package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    //Créations des différents éléments

    ContentValues val = new ContentValues();
    ListView lst;
    String arrNom [], arrDate [], arrDisplay [];
    int arrParti [];
    SQLiteDatabase db;
    Button btnRetour;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) { // A la création de la page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        lst = (ListView) findViewById(R.id.listdata);  // Afiliation a la listeView
        btnRetour = (Button) findViewById(R.id.btn_retour);// Afiliation au Bouton

        db = openOrCreateDatabase("Events.db",SQLiteDatabase.CREATE_IF_NECESSARY, null); // Si La bdd Events.bd existe pas il créer une base de donnée.

        Cursor c = db.query("Event", null,null,null,null,null,null); // Curseur pour la Table

        // Initialisation des Tableau de la taille de la BDD
        arrNom = new String[c.getCount()];
        arrDate = new String[c.getCount()];
        arrParti = new int[c.getCount()];
        arrDisplay = new String[c.getCount()];

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

            String partiString = Integer.toString(arrParti [i]); // on converti le nombre de participants en String

            arrDisplay [i] = "Evenement : " + arrNom[i] +" \n Date : "+ arrDate [i] + " \n Participants : " + partiString ; // Tableau qui va afficher TOUT les élements
            Toast.makeText(this,"Nom : "+ arrNom[i],1000).show();
            c.moveToNext(); // Prochaine Ligne du tableau
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrDisplay); // Méthode spécifique aux ListView qui demande 1 : Activity de la listView 2: Layout de la ListView 3: Un tableau avec TOUT les infos qu'il va afficher
        lst.setAdapter(adp); // on lie notre adapteur a notre liste

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() { // quand on appuie sur un item du listeneur affiche le titre de l'éèenement sur le quel on a cliqué
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Titre"+ arrNom[position],1000).show();
            }
        });
    }
}