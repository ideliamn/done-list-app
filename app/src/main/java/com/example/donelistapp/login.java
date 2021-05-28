package com.example.donelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText et_email, et_password;
    FirebaseAuth auth;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        auth = FirebaseAuth.getInstance();

        email = et_email.getText().toString();
        password = et_password.getText().toString();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}