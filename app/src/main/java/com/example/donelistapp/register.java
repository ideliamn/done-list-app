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

    public EditText et_nama, et_email, et_password;
    public Button btn_reg;
    public FirebaseAuth auth;
    public DatabaseReference db_ref;
    public String nama, email, password, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //firebase variables
        auth = FirebaseAuth.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("users");

        //declare xml components
        et_nama = findViewById(R.id.et_reg_nama);
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
        nama = et_nama.getText().toString();
        email = et_email.getText().toString();
        password = et_password.getText().toString();

        if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(email) ||
        TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Tolong isi semua kolom yang tersedia",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            if (password.length() < 6) {
                Toast.makeText(this, "Password terlalu pendek",
                        Toast.LENGTH_SHORT).show();
            }
            else {
//                progressBar.setVisibility(View.VISIBLE);
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

//                            String id = db_ref.push().getKey();
                            HashMap<Object, String> data = new HashMap<>();
                            data.put("uid", uid);
                            data.put("nama", nama);
                            data.put("email", email);
                            data.put("password", password);

                            db_ref.child(uid).setValue(data);

//                            Intent login = new Intent(getApplicationContext(), login.class);
//                            startActivity(login);
                            startActivity(new Intent(getApplicationContext(), login.class));
                            finish();
                        }
                        else {
                            Toast.makeText(register.this, "Terjadi kesalahan. Silakan coba lagi.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}