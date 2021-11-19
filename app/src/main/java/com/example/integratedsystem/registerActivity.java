package com.example.integratedsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    TextInputEditText emailreget,passreget;
    Button regbt;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        regbt=(Button)findViewById(R.id.regbt) ;

        emailreget = findViewById(R.id.emailreget);
        passreget = findViewById(R.id.passreget);


        mAuth = FirebaseAuth.getInstance();

        regbt.setOnClickListener(view -> {
            createUser();
        });
    }
        private void createUser(){
            String email = emailreget.getText().toString();
            String pass = passreget.getText().toString();

            if(TextUtils.isEmpty(email)) {
                emailreget.setError("Email cannot be empty!");
                emailreget.requestFocus();
            }
            else if (TextUtils.isEmpty(pass)) {
                passreget.setError("Password cannot be empty!");
                passreget.requestFocus();
            }else {
                mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(registerActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(registerActivity.this, loginActivity.class));
                        }else {
                            Toast.makeText(registerActivity.this, "Registered Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }

