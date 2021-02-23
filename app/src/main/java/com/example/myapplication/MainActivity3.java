package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    /*DatabaseHelper db;


    ArrayList<String> listItem;
    ArrayAdapter adapter;*/

    ContentValues val = new ContentValues();
    ListView lst;
    String arrNom [], arrDate [], arrDisplay [];
    int arrParti [];
    SQLiteDatabase db;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        lst = (ListView) findViewById(R.id.listdata);
        db = openOrCreateDatabase("Events.db",SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor c = db.query("Event", null,null,null,null,null,null);

        arrNom = new String[c.getCount()];
        arrDate = new String[c.getCount()];
        arrParti = new int[c.getCount()];
        arrDisplay = new String[c.getCount()];

        c.moveToFirst();
        for (int i=0 ; i< arrNom.length;i++)
        {
            arrNom [i] = c.getString(1);
            arrDate [i] = c.getString(3);
            arrParti [i] = c.getInt(2);
            String partiString = Integer.toString(arrParti [i]);
            arrDisplay [i] = "Evenement : " + arrNom[i] +"  Date : "+ arrDate [i] + " Participants : " + partiString ;
            Toast.makeText(this,"Nom : "+ arrNom[i],1000).show();
            c.moveToNext();
        }

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrDisplay);
        lst.setAdapter(adp);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Titre"+ arrNom[position],1000).show();
            }
        });
    }
}