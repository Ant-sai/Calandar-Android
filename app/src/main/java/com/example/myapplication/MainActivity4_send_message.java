package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity4_send_message extends AppCompatActivity {

    Button btnSend;
    final int SEND_SMS_PERMISSION = 1;

    EditText number;
    EditText message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4_send_message);

        btnSend = findViewById(R.id.send);
        number = findViewById(R.id.inputNumber);
        message = findViewById(R.id.inputMessage);
        btnSend.setEnabled(true);
        if(checkPermission(Manifest.permission.SEND_SMS)){
            btnSend.setEnabled(true);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = number.getText().toString();
                String smsMessage = message.getText().toString();
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
    }
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(MainActivity4_send_message.this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}