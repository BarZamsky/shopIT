package com.example.barza.ourshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class JeansActivity extends AppCompatActivity {

    Button btnBack, btnAdd;

    FirebaseDatabase db;
    DatabaseReference users, products;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeans);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        products = db.getReference("Products");

        btnBack = (Button) findViewById(R.id.btnBack);
        btnAdd = (Button) findViewById(R.id.btnAdd);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJeans();
            }
        });

    }

    public void back() {
        Intent s = new Intent(getApplicationContext(), ShopActivity.class);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        startActivity(s);
    }

    public void addJeans() {
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String name = "Levis_Jeans";
                final Product p = dataSnapshot.child(name).getValue(Product.class);
                Log.d("Product:::", p.toString());
                User login = (User) getIntent().getSerializableExtra("User");
                Log.d("user:::", login.toString());
                login.getCart().add(p);
                p.set_stock(p.get_stock()-1);
                Log.d("user:::", login.toString());
                Toast.makeText(JeansActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { };
        });
    };
}
