package com.example.barza.ourshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barza.ourshop.Classes.CardNumber;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.barza.ourshop.Classes.User;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput;
    private EditText nameInput;
    private EditText lastnameInput;

    FirebaseDatabase db;
    DatabaseReference users, card;
    Button btnToRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        card = db.getReference("CardDetails");

        this.emailInput = (EditText)findViewById(R.id.emailInputRegistration);
        this.passwordInput = (EditText)findViewById(R.id.passwordInputRegistration);
        this.nameInput = (EditText)findViewById(R.id.nameInputRegistration);
        this.lastnameInput = (EditText)findViewById(R.id.lastnameInputRegistration);

        btnToRegister = (Button) findViewById(R.id.btnReg);
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnRegister(emailInput.getText().toString(),
                        passwordInput.getText().toString(),
                        nameInput.getText().toString(),
                        lastnameInput.getText().toString());
            }
        });

    }

    public void OnRegister(final String email, String pass, String fName, String lName){
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        final User user = new User(email, pass, fName, lName);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(user.getEmail().replace(".", "|")).exists()) {
                    Toast.makeText(RegistrationActivity.this, "This email is already exist", Toast.LENGTH_SHORT).show();
                } else {
                    users.child(user.getEmail().replace(".", "|")).setValue(user);
                    Toast.makeText(RegistrationActivity.this, "Success Register!", Toast.LENGTH_SHORT).show();
                    CardNumber cn = new CardNumber("", "", "");
                    card.child(email.replace(".", "|")).setValue(cn);
                    Intent s = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
