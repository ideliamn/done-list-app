package com.example.donelistapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class home extends AppCompatActivity {

    public FirebaseAuth auth;
    public DatabaseReference db_ref, name_ref;
    TextView tv_hello_user;
    public String email, id, name, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_hello_user = findViewById(R.id.tv_hello_user);

        auth = FirebaseAuth.getInstance();
        db_ref =  FirebaseDatabase.getInstance().getReference().child("users");
        email = auth.getCurrentUser().getEmail();
        uid = auth.getCurrentUser().getUid();

        // get user's name
        name_ref = db_ref.child("idelia").child("nama");
        name_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        tv_hello_user.setText("Hello, "+name+" with "+email+" and UID "+uid+"");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}