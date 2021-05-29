package com.example.donelistapp;

import android.app.ListActivity;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class home extends AppCompatActivity {

    public FirebaseAuth auth;
    public DatabaseReference db_ref, db_ref_activity;
    TextView tv_hello_user;
    public String email, id, name, uid;

    RecyclerView recyclerView;
    ActivityAdapter activityAdapter;
    ArrayList<ModelActivity> modelActivityArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv_hello_user = findViewById(R.id.tv_hello_user);

        // firebase variables
        auth = FirebaseAuth.getInstance();
        email = auth.getCurrentUser().getEmail();
        uid = auth.getCurrentUser().getUid();
        db_ref = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        // get user's name
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_name = dataSnapshot.child("name").getValue().toString();
                tv_hello_user.setText("Hello, " + user_name + "!");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // recycler view
        recyclerView = (RecyclerView) findViewById(R.id.rv_activity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        activityAdapter = new ActivityAdapter(this, modelActivityArrayList);
        recyclerView.setAdapter(activityAdapter);
        db_ref_activity = FirebaseDatabase.getInstance().getReference("activities").child(uid);
        populateRecyclerView();

        // floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), add_activity.class));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void populateRecyclerView() {
        db_ref_activity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modelActivityArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String activity = ds.child("activity").getValue().toString();
                    ModelActivity modelActivity = new ModelActivity(activity);
                    modelActivityArrayList.add(modelActivity);
                }
                Collections.reverse(modelActivityArrayList);
                activityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}