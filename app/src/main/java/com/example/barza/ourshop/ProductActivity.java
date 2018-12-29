package com.example.barza.ourshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barza.ourshop.Classes.Product;
import com.example.barza.ourshop.Classes.User;
import com.example.barza.ourshop.SERVER.Server;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductActivity extends AppCompatActivity {
    private EditText pName, pPrice, pStock, pDesc;
    private Button btnAdd;

    FirebaseDatabase db;
    DatabaseReference products;

    User user;
    Server server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        server = new Server();

        db = FirebaseDatabase.getInstance();
        products = db.getReference("Products");
        user = (User) getIntent().getSerializableExtra("User");
        pName = (EditText) findViewById(R.id.pName);
        pPrice = (EditText) findViewById(R.id.pPrice);
        pStock = (EditText) findViewById(R.id.pStock);
        pDesc = (EditText) findViewById(R.id.pDesc);

        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct(pName.getText().toString(), pDesc.getText().toString(), Double.parseDouble(pPrice.getText().toString()), Integer.parseInt(pStock.getText().toString()));
            }
        });
    }

    public void addProduct(String name, String desc, double price, int stock){
        Product p = new Product(name, desc, price, stock);
        db = FirebaseDatabase.getInstance();
        products = db.getReference("Products");
        products.child(p.get_name()).setValue(p);
        Toast.makeText(ProductActivity.this, "Product Added to DB!", Toast.LENGTH_SHORT).show();
        Intent s = new Intent(getApplicationContext(), ManagerActivity.class);
        s.putExtra("User", user);
        startActivity(s);
    }
}
