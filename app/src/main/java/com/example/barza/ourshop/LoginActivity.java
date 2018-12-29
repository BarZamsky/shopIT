package com.example.barza.ourshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.example.barza.ourshop.Classes.CardNumber;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.barza.ourshop.Classes.User;

public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference users, card;

    EditText emailInput;
    EditText passInput;
    Button btnToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        card = db.getReference("CardDetails");

        emailInput = (EditText)findViewById(R.id.emailInputLogin);
        passInput = (EditText)findViewById(R.id.passwordInputLogin);
        btnToLogin = (Button) findViewById(R.id.btnLogin);
        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(emailInput.getText().toString(),
                        passInput.getText().toString());
            }
        });
    }

    public void signIn(final String email, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(email.replace(".", "|")).exists()) {
                    if (!email.replace(".", "|").isEmpty()) {
                        User login = dataSnapshot.child(email.replace(".", "|")).getValue(User.class);
                        if (login.getEmail().equals("bar@gmail.com")) {
                            if (login.getPassword().equals(password)) {
                                Intent s = new Intent(getApplicationContext(), ManagerActivity.class);
                                s.putExtra("User", login);
                                startActivity(s);
                            }
                        }
                        else if (login.getPassword().equals(password)) {
                            Toast.makeText(LoginActivity.this, "Success Login!", Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(), ShopActivity.class);
                            s.putExtra("email", email);
                            s.putExtra("User", login);
                            CardNumber cn = new CardNumber("", "", "");
                            card.child(email.replace(".", "|")).setValue(cn);
                            startActivity(s);
                        } else {
                            Toast.makeText(LoginActivity.this, "password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "This email is not registered", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    }
