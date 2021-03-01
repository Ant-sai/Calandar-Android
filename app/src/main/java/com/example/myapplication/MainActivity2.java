package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener{


    //Création Bouton et BDD
    DatabaseHelper db;
    Button btnAddData;
    Button btnChooseDate;
    EditText editAddName;
    EditText editAddParti;
    Spinner spinnerType;
    String typeEvent= "";
    List dateList = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        spinnerType = (Spinner) findViewById(R.id.spinner);
        List typeList = new ArrayList();
        typeList.add("Anniverssaires");
        typeList.add("Réunion");
        typeList.add("Rendez-vous");

        //Initialisation Bouton et BDD

        db = new DatabaseHelper(this);
        btnAddData = findViewById(R.id.add_data);
        editAddName = findViewById(R.id.add_name);
        editAddParti = findViewById(R.id.add_part);
        btnChooseDate =  findViewById(R.id.buttonDate);
        TextView textView = (TextView) findViewById(R.id.textView3);


        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,typeList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter2);
        spinnerType.setOnItemSelectedListener(this);



        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.myapplication.DatePicker(); // Appel du fragment Date Picker et met le resultat dans l'objet datePicker
                datePicker.show(getSupportFragmentManager(), "date picker"); // permet de voir le DatePicker qu'on viens d'appeler
            }
        });

         // Bouton pour ajouter les valeurs dans la BDD
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editAddName.getText().toString(); // Conversion du EditText en String
                String textViewDate = textView.getText().toString(); // Conversion du EditText avec la date en String
                int part = Integer.parseInt(editAddParti.getText().toString()); // Conversion du EditText nb de participant en Integer
                if (dateList.contains(textViewDate)){
                    Toast.makeText(MainActivity2.this, "Votre date est déja utilisé", Toast.LENGTH_SHORT).show();
                }
                if (!name.equals("") && db.insertData(name,part,textViewDate,typeEvent)) { // Si le nom de l'event et que l'insertion dans la BDD sont TRUE (appel de la méthode insert data de la classe DataBaseHelper)
                    dateList.add(textViewDate);
                    Toast.makeText(MainActivity2.this, "Données ajoutées", Toast.LENGTH_SHORT).show(); // Affichage du succès d'ajout de données
                    // Remise a 0 des champs
                    editAddName.setText("");
                    editAddParti.setText("");
                }else {
                    Toast.makeText(MainActivity2.this,"données non ajoutées " ,Toast.LENGTH_SHORT).show(); // Affichage si l'ajoue a fail
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        // Création d'une Instance de calendar pour enregistrer la valeur de l'année / mois / jour
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);


        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime()); //String contenant les informations reçus du calandar

        Toast.makeText(MainActivity2.this,currentData,Toast.LENGTH_SHORT).show();
        TextView textView = (TextView) findViewById(R.id.textView3); // Ecriture de la date dnas le textView
        textView.setText(currentData);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        typeEvent = text;
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}