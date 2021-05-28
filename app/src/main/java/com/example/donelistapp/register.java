package com.example.donelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class register extends AppCompatActivity {

    EditText et_nama = findViewById(R.id.et_nama);
    EditText et_email = findViewById(R.id.et_email);
    EditText et_password = findViewById(R.id.et_password);

    String nama = et_nama.getText().toString();
    String email = et_email.getText().toString();
    String password = et_password.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}