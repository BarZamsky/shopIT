package com.example.barza.ourshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.barza.ourshop.Classes.Product;
import com.example.barza.ourshop.Classes.User;
import com.example.barza.ourshop.SERVER.Server;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SkirtsActivity extends AppCompatActivity {

    Button btnBack, btnDark, btnLight;

    FirebaseDatabase db;
    DatabaseReference users, products;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skirts);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        products = db.getReference("Products");

        btnBack = (Button) findViewById(R.id.btnBack);
        btnDark = (Button) findViewById(R.id.btnDark);
        btnLight = (Button) findViewById(R.id.btnLight);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        btnDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDarkSkirt();
            }
        });

        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLightSkirt();
            }
        });
    }

    public void back() {
        Intent s = new Intent(getApplicationContext(), ShopActivity.class);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        startActivity(s);
    }

    public void addDarkSkirt() {
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String name = "Dark Jeans Skirt";
                if(!dataSnapshot.child(name).exists()) {
                    Product p = new Product(name, "dark skirt", 129.90, 15);
                    products.child(name).setValue(p);
                }
                final Product p = dataSnapshot.child(name).getValue(Product.class);
                Log.d("Product:::", p.toString());
                User login = (User) getIntent().getSerializableExtra("User");
                Log.d("user:::", login.toString());
                login.getCart().add(p);
                p.set_stock(p.get_stock()-1);
                Log.d("user:::", login.toString());
                Toast.makeText(SkirtsActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { };
        });
    };

    public void addLightSkirt() {
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String name = "Light Jeans Skirt";
                if(!dataSnapshot.child(name).exists()) {
                    Product p = new Product(name, "heeled boots black", 179.99, 10);
                    products.child(name).setValue(p);
                }
                final Product p = dataSnapshot.child(name).getValue(Product.class);
                Log.d("Product:::", p.toString());
                User login = (User) getIntent().getSerializableExtra("User");
                login.getCart().add(p);
                p.set_stock(p.get_stock()-1);
                Log.d("user:::", login.toString());
                Toast.makeText(SkirtsActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { };
        });
    };
}

