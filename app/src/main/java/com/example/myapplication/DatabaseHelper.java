package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "Events.db";
    private static final String DB_Table = "Event";

    /*colonne*/
    private static final String ID = "ID";
    private static final String NAME = "TITRE";
    private static final String DATE = "DATE";
    private static final String PARTICIPANTS = "PARTICIPANTS";


    private  static final String CREATE_TABLE = "CREATE TABLE " + DB_Table + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +NAME +" TEXT, "  + PARTICIPANTS +" INTEGER, " +DATE +" TEXT" + ")"; // Requete SQL pour créer la table

    public  DatabaseHelper (Context context){
        super(context, DB_Name, null,1); // appel à la classe parent
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // création de la Table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // Si on change la table on drop l'ancienne et on encréer une nouvelle
        db.execSQL("DROP TABLE IF EXISTS " +DB_Table);
        onCreate(db);
    }

    // méthode d'ajout dans la bdd
    public boolean insertData (String name,int parti, String date){
        SQLiteDatabase db = this.getWritableDatabase(); // On met en mode écriture le BDD

        ContentValues contentValues = new ContentValues(); // Objet qui contiendra le nom de notre colonne et la valeur qu'on souhaite ajouter

        //Ajoute des 3 colonnes
        contentValues.put(NAME,name);
        contentValues.put(PARTICIPANTS,parti);
        contentValues.put(DATE,date);

        Log.v("test", "contentValues"); // log pour voir si cela est bien fait

        long result = db.insert(DB_Table, null,contentValues); // insersion des datas dans la BDD

        return  result != -1; // si la ligne à été ajouter alors renvoie rien si nn -1
    }
    // method to view data
    public  Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase(); // Mode Lecrutre BDD
        String query = "Select * from " + DB_Table;     // Sélection de tt les BDD dans la table
        Log.v("test",query);
        Cursor cursor = db.rawQuery(query, null); // création d'un objet curseur qui contient les valeur de la ligne

        return cursor;
    }
}
