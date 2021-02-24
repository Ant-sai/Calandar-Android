package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAct2 = (Button) findViewById(R.id.btn_act2); // On créer les bouttons qu'on lie a a l'ID de l'xml
        Button btnAct3 = (Button) findViewById(R.id.btn_act3);

        btnAct2.setOnClickListener(new View.OnClickListener() { // Méthode quand on clique
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));  // On lie le bouton a l'activité 2
            }


        });
        btnAct3.setOnClickListener(new View.OnClickListener() {   // Méthode quand on clique
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity3.class)); // On lie le bouton a l'activité 3
            }
        });
    }
}