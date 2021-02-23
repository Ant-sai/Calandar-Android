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


    private  static final String CREATE_TABLE = "CREATE TABLE " + DB_Table + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +NAME +" TEXT, "  + PARTICIPANTS +" INTEGER, " +DATE +" TEXT" + ")";

    public  DatabaseHelper (Context context){
        super(context, DB_Name, null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        Log.v("ceciestunstring","DKJBFKFJBGIDUFBGFPIBQEJFRBFIPHBGQEIPBRIGTRBIPGBIFBDSIUGBFIFDBEQIHTIPURG");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +DB_Table);
        onCreate(db);
    }

    // méthode d'ajout dans la bdd
    public boolean insertData (String name,int parti, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("INSERT INTO" + DB_Table + "( " + NAME +" , " +PARTICIPANTS +" )  VALUES ( ' " + name + "' , '" + parti + "' );" );
        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME,name);
        contentValues.put(PARTICIPANTS,parti);
        contentValues.put(DATE,date);
        Log.v("test", "contentValues");
        long result = db.insert(DB_Table, null,contentValues);

        return  result != -1;
    }
    // method to view data
    public  Cursor viewData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_Table;
        Log.v("test",query);
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
