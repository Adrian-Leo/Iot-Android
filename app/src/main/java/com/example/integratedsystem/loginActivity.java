package com.example.integratedsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    TextInputEditText emaillogin, passlogin;
    TextView regtv;
    Button loginbt;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emaillogin = findViewById(R.id.emaillogin);
        passlogin = findViewById(R.id.passlogin);
        regtv = findViewById(R.id.regtv);
        loginbt = findViewById(R.id.loginbt);

        mAuth = FirebaseAuth.getInstance();

        loginbt.setOnClickListener(view ->{
            loginuser();
        });
        regtv.setOnClickListener(view ->{
            startActivity(new Intent(loginActivity.this, registerActivity.class));
        });
    }
        private void loginuser(){
            String email = emaillogin.getText().toString();
            String pass = passlogin.getText().toString();

            if(TextUtils.isEmpty(email)) {
                emaillogin.setError("Email cannot be empty!");
                emaillogin.requestFocus();
            }
            else if (TextUtils.isEmpty(pass)) {
                passlogin.setError("Password cannot be empty!");
                passlogin.requestFocus();
            }else {
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(loginActivity.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(loginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(loginActivity.this, "Logged in Fail " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
        }
}
