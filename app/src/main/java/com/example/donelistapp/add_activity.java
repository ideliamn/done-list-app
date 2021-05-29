package com.example.donelistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class add_activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_what_activity;
    EditText et_activity;
    Button btn_add_activity;

    FirebaseAuth auth;
    DatabaseReference db_ref, act_ref;
    String uid, activity, act_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        tv_what_activity = findViewById(R.id.tv_what_activity);
        et_activity = findViewById(R.id.et_activity);
        btn_add_activity = findViewById(R.id.btn_add_activity);

        // get user's name
        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        db_ref =  FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.child("name").getValue().toString();
                tv_what_activity.setText("What are you gonna do, "+user_name+"?");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_add_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_activity: {
                addActivity();
                finish();
            }
        }
    }

    public void addActivity() {

        activity = et_activity.getText().toString();

        if (TextUtils.isEmpty(activity)) {
            Toast.makeText(this, "Please fill the activity", Toast.LENGTH_SHORT).show();
        }
        else {
            act_ref =  FirebaseDatabase.getInstance().getReference().child("activities").child(uid);
            act_id = act_ref.push().getKey();
            HashMap<Object, String> data = new HashMap<>();
            data.put("activity", activity);
            act_ref.child(act_id).setValue(data);
            Toast.makeText(this, "Success add activity!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), home.class));
            finish();
        }
    }
}