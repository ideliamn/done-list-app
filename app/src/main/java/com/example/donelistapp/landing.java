package com.example.donelistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class landing extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button btn_register = findViewById(R.id.btn_register);
        Button btn_login = findViewById(R.id.btn_login);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        //Mengecek Keberadaan User
        FirebaseAuth.AuthStateListener listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Mengecek apakah ada user yang sudah login / belum logout
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //Jika ada, maka halaman akan langsung berpidah pada MainActivity
                    startActivity(new Intent(getApplicationContext(), home.class));
                    finish();
                }
            }
        };
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {
                Intent register = new Intent(getApplicationContext(), register.class);
                startActivity(register);
                break;
            }
            case R.id.btn_login: {
                Intent login = new Intent(getApplicationContext(), login.class);
                startActivity(login);
                break;
            }
        }
    }

}