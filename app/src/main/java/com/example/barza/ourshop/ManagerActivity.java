package com.example.barza.ourshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.barza.ourshop.Classes.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerActivity extends AppCompatActivity {
    private Button btnLogout, btnApproveOrders, btnAddProduct;
    private TextView helloText;
    User manager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        manager =  (User) getIntent().getSerializableExtra("User");

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnApproveOrders = (Button) findViewById(R.id.btnApproveOrders);
        btnAddProduct = (Button) findViewById(R.id.btnAddProduct);


        helloText = (TextView) findViewById(R.id.helloText);
        helloText.setText("Hello "+ manager.getName()+ " "+manager.getLastName());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        btnApproveOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                approveOrders();
            }
        });
    }

    public void logout() {
        Intent s = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(s);
    }

    public void addProduct() {
        Intent s = new Intent(getApplicationContext(), ProductActivity.class);
        s.putExtra("User", manager);
        startActivity(s);
    }

    public void approveOrders() {
        Intent s = new Intent(getApplicationContext(), ApproveActivity.class);
        s.putExtra("User", manager);
        startActivity(s);
    }
}
