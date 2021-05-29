package com.example.donelistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity implements View.OnClickListener {

    public EditText et_name, et_email, et_password;
    public Button btn_reg;
    public FirebaseAuth auth;
    public DatabaseReference db_ref;
    public String name, email, password, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //firebase variables
        auth = FirebaseAuth.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("users");

        //declare xml components
        et_name = findViewById(R.id.et_reg_name);
        et_email = findViewById(R.id.et_reg_email);
        et_password = findViewById(R.id.et_reg_password);
        btn_reg = findViewById(R.id.btn_register);

        btn_reg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {
                checkUserData();
                break;
            }
        }
    }

    public void checkUserData() {
        //put into string variables
        name = et_name.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
        TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all columns.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            if (password.length() < 6) {
                Toast.makeText(this, "Password is too short. Minimum character is 6.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                createAccount();
            }
        }
    }

    public void createAccount() {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(register.this, "Authentication success with e-mail "+email+"",
                                    Toast.LENGTH_SHORT).show();

                            uid = auth.getCurrentUser().getUid();

                            HashMap<Object, String> data = new HashMap<>();
                            data.put("uid", uid);
                            data.put("name", name);
                            data.put("email", email);
                            data.put("password", password);
                            db_ref.child(uid).setValue(data);

                            startActivity(new Intent(getApplicationContext(), login.class));
                            finish();
                        }
                        else {
                            Toast.makeText(register.this, "Register failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}