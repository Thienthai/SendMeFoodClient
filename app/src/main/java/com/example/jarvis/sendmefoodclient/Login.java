package com.example.jarvis.sendmefoodclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView number,passwords;
    Button signin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        number = (TextView) findViewById(R.id.number);
        passwords = (TextView) findViewById(R.id.passwords);
        signin_btn = (Button) findViewById(R.id.signin_btn);

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference db_rf = db.getReference("User");

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialg = new ProgressDialog(Login.this);
                dialg.setMessage("Processing...");
                dialg.show();


                db_rf.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(number.getText().toString()).exists()) {

                            dialg.dismiss();
                            User usr = dataSnapshot.child(number.getText().toString()).getValue(User.class);
                            usr.setNumber(number.getText().toString());
                            if (Boolean.parseBoolean(usr.getIsStaff())) {
                                if(usr.getPassword().equals(passwords.getText().toString())){
                                    Intent intent = new Intent(Login.this,Home.class);
                                    Current.usrCurrent = usr;
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(Login.this,"Wrong password",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "Signin Fail not staff account!!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            dialg.dismiss();
                            Toast.makeText(Login.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
