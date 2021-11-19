package com.example.integratedsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //inisialisasi textview
    private TextView temp, humidity, soil, lamp;
    private SwitchCompat pumpsw, lampsw;
    Button logoutbt;

    FirebaseAuth mAuth;

    //reference untuk firebase (koneksi server/host firebase)
    private Firebase mref, mref2,mref3,mref4,mref5,mref6,mref7,mref8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //membaca komponen nilai
        temp=(TextView)findViewById(R.id.temp);
        humidity=(TextView)findViewById(R.id.humidity);
        soil=(TextView)findViewById(R.id.soil);
        lamp=(TextView)findViewById(R.id.lamp);
        pumpsw=(SwitchCompat)findViewById(R.id.pumpsw);
        lampsw=(SwitchCompat)findViewById(R.id.lampsw);
        logoutbt=(Button)findViewById(R.id.logoutbt);
        mAuth= FirebaseAuth.getInstance();

        logoutbt.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, loginActivity.class));
        });


        //buka koneksi ke firebase
        mref=new Firebase("https://integratedsystem-86551-default-rtdb.asia-southeast1.firebasedatabase.app/temp");
        mref2=new Firebase("https://integratedsystem-86551-default-rtdb.asia-southeast1.firebasedatabase.app/humidity");
        mref3=new Firebase("https://integratedsystem-86551-default-rtdb.asia-southeast1.firebasedatabase.app/soil");
        mref4=new Firebase("https://integratedsystem-86551-default-rtdb.asia-southeast1.firebasedatabase.app/lamp");
        mref5=new Firebase("https://integratedsystem-86551-default-rtdb.asia-southeast1.firebasedatabase.app/pumpsw");
        mref6=new Firebase("https://integratedsystem-86551-default-rtdb.asia-southeast1.firebasedatabase.app/lampsw");

        //proses realtime
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ambil nilai field value
                String value1= dataSnapshot.getValue(String.class);

                //tampilkan di komponen nilai
                temp.setText(value1);

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }


         });
        mref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value2= dataSnapshot.getValue(String.class);

                humidity.setText(value2);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value3= dataSnapshot.getValue(String.class);

                soil.setText(value3);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
            });
        mref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value= dataSnapshot.getValue(String.class);

                lamp.setText(value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        pumpsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    
                }
            }
        });

        //kontrol
        pumpsw= findViewById(R.id.pumpsw);
        lampsw= findViewById(R.id.lampsw);

//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("https://sensor-e78b7-default-rtdb.firebaseio.com/pumpsw");
//        DatabaseReference usersRef = ref.child("sensor");

        mref7=new Firebase("https://sensor-e78b7-default-rtdb.firebaseio.com/nyalapompa");
        mref8=new Firebase("https://sensor-e78b7-default-rtdb.firebaseio.com/nyalalampu");

        pumpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pumpsw.isChecked()){
                    int x = 1;
                    mref7.setValue(x);
                } else{
                    int x = 0;
                    mref7.setValue(x);
                }
            }
        });

        lampsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lampsw.isChecked()){
                    int x = 1;
                    mref8.setValue(x);
                } else{
                    int x = 0;
                    mref8.setValue(x);
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, loginActivity.class));
        }
    }
}