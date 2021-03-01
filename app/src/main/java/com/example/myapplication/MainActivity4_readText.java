package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity4_readText extends AppCompatActivity {

    Button b_load;
    TextView textView5;
    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4_read_text);

        //Request permission
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        b_load = (Button) findViewById(R.id.b_load);
        textView5 = (TextView) findViewById(R.id.textView5);

        b_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private String readText (String input){
        File file = new File(Environment.getExternalStorageState(), input);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    private void PerformFileSearch(){
        Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("Text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
                if (data != null){
                    Uri uri = data.getData();
                    String path = uri.getPath();
                    path = path.substring(path.indexOf(":")+1);
                    Toast.makeText(this,"" + path, Toast.LENGTH_SHORT).show();
                    textView5.setText(readText(path));
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission refusée", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}