package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatabaseHelper db;

    Button add_data;
    Button buttonDate;
    EditText add_name;
    EditText add_part;

    ListView userlist;


    ArrayList <String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);
        userlist = findViewById(R.id.users_list);
        add_part = findViewById(R.id.add_part);
        buttonDate =  findViewById(R.id.buttonDate);
        TextView textView = (TextView) findViewById(R.id.textView3);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.myapplication.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = userlist.getItemAtPosition(position).toString();

                Toast.makeText(MainActivity2.this,""+text, Toast.LENGTH_SHORT).show();
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = add_name.getText().toString();
                String textViewDate = textView.getText().toString();
                int part = Integer.parseInt(add_part.getText().toString());
                if (!name.equals("") && db.insertData(name,part,textViewDate)) {
                    Toast.makeText(MainActivity2.this, "Données ajoutées", Toast.LENGTH_SHORT).show();
                    add_name.setText("");
                    listItem.clear();
                    viewData();
                }else {
                    Toast.makeText(MainActivity2.this,"données non ajoutées" + db.insertData(name,part,textViewDate) ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void viewData() {
        Cursor cursor = db.viewData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Pas d'évènements", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                listItem.add(cursor.getString(1) +"     "+ cursor.getString(2)); // index1 = id
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);

        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentData = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Toast.makeText(MainActivity2.this,currentData,Toast.LENGTH_SHORT).show();
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(currentData);
    }
}