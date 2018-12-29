package com.example.barza.ourshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.barza.ourshop.Classes.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ConfirmActivity extends AppCompatActivity {
    private TextView number;
    private Button btnLogout;
    private Order order;
    FirebaseDatabase db;
    DatabaseReference orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        db = FirebaseDatabase.getInstance();
        orders = db.getReference("Orders");

        number = (TextView) findViewById(R.id.number);
        String orderNum = "" + (int) (new Random().nextInt(9999-1000)+1000);
        String ans =  ""+ number.getText() + orderNum;
        number.setText(ans);
        order = new Order(orderNum, false);
        orders.child(order.get_orderNumber()).setValue(order);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    public void logout() {
        Intent s = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(s);
    }

}
