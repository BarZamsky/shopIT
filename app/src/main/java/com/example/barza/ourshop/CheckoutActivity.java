package com.example.barza.ourshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barza.ourshop.Classes.CardNumber;
import com.example.barza.ourshop.Classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckoutActivity extends AppCompatActivity {
    private EditText cardInput;
    private EditText cvvInput;
    private EditText expiryInput;
    private TextView total;

    FirebaseDatabase db;
    DatabaseReference users, card;
    Button approve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        db = FirebaseDatabase.getInstance();
//        users = db.getReference("Users");
        card = db.getReference("CardDetails");

        Bundle b = getIntent().getExtras();
        double tPrice = b.getDouble("Total");

       // double t = getIntent().getDoubleExtra("Total");
        total = (TextView) findViewById(R.id.total);
        total.setText("Total: "+ tPrice);
        cardInput = (EditText) findViewById(R.id.cardNumber);
        cvvInput = (EditText) findViewById(R.id.cvv);
        expiryInput = (EditText) findViewById(R.id.expiry);

        approve = (Button) findViewById(R.id.btnApprove);

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onApprove(cardInput.getText().toString(), cvvInput.getText().toString(),
                            expiryInput.getText().toString());
            }
        });
    }
    public void onApprove(String cardNumber, String cvv, String expiry){
        db = FirebaseDatabase.getInstance();
        card = db.getReference("CardDetails");

        final CardNumber cardDetails = new CardNumber(cardNumber, cvv, expiry);
        final User login = (User) getIntent().getSerializableExtra("User");
        card.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(login.getEmail().replace(".", "|")).exists()) {
                    card.child(login.getEmail().replace(".", "|")).setValue(cardDetails);
                    Toast.makeText(CheckoutActivity.this, "Payment Approved!", Toast.LENGTH_SHORT).show();
                    Intent s = new Intent(getApplicationContext(), ConfirmActivity.class);
                    startActivity(s);
                } else {
                    card.child(login.getEmail().replace(".", "|")).setValue(cardDetails);
                    Toast.makeText(CheckoutActivity.this, "Payment Approved!", Toast.LENGTH_SHORT).show();
                    Intent s = new Intent(getApplicationContext(), ConfirmActivity.class);
                    startActivity(s);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
