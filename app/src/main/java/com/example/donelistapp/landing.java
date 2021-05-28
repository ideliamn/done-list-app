package com.example.donelistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class landing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button btn_register = findViewById(R.id.btn_register);
        Button btn_login = findViewById(R.id.btn_login);
    }

    public void click_register(View view) {
        Intent register = new Intent(getApplicationContext(), register.class);
        startActivity(register);
    }

    public void click_login(View view) {
        Intent login = new Intent(getApplicationContext(), login.class);
        startActivity(login);
    }

}