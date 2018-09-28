package com.example.jarvis.sendmefoodclient;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarvis.sendmefoodclient.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    TextView name,number,psswd;
    Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (TextView) findViewById(R.id.name_regis);
        number = (TextView) findViewById(R.id.number_regis);
        psswd = (TextView) findViewById(R.id.pass_regis);

        register_btn = (Button) findViewById(R.id.register_btn);

        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference db_rf = db.getReference("User");

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialg = new ProgressDialog(Register.this);
                dialg.setMessage("Processing...");
                dialg.show();

                db_rf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(number.getText().toString()).exists()){
                            dialg.dismiss();
                            Toast.makeText(Register.this,"Phone number already register", Toast.LENGTH_SHORT).show();
                        }else {
                            dialg.dismiss();
                            User usr = new User(name.getText().toString(),psswd.getText().toString(),number.getText().toString());
                            usr.setIsStaff("true");
                            db_rf.child(number.getText().toString()).setValue(usr);
                            Toast.makeText(Register.this,"Register Success", Toast.LENGTH_SHORT).show();
                            finish();
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
