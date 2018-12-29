package com.example.barza.ourshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

public class ShoesActivity extends AppCompatActivity {

    Button btnBack, btnAdidas, btnBoots;

    String sizeBoots = "";
    String sizeAdidas= "";
    private Spinner dropDownBoots, dropDownAdidas;
    private static final String[] paths = {"38", "39", "40"};

    FirebaseDatabase db;
    DatabaseReference users, products;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes);

        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        products = db.getReference("Products");

        btnBack = (Button) findViewById(R.id.btnBack);
        btnAdidas = (Button) findViewById(R.id.btnAdidas);
        btnBoots = (Button) findViewById(R.id.btnBoots);

        dropDownBoots = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paths);
        dropDownBoots.setAdapter(adapter);
        dropDownBoots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sizeBoots = "Size: 38";
                        break;
                    case 1:
                        sizeBoots = "Size: 39";
                        break;
                    case 2:
                        sizeBoots = "Size: 40";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dropDownAdidas = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, paths);
        dropDownAdidas.setAdapter(adapter1);
        dropDownAdidas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sizeAdidas = "Size: 38";
                        break;
                    case 1:
                        sizeAdidas = "Size: 39";
                        break;
                    case 2:
                        sizeAdidas = "Size: 40";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        btnBoots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBoots();
            }
        });

        btnAdidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdidas();
            }
        });
    }

    public void back() {
        Intent s = new Intent(getApplicationContext(), ShopActivity.class);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        if(!sizeAdidas.isEmpty()) s.putExtra("SizeAdidas", sizeAdidas);
        if(!sizeBoots.isEmpty()) s.putExtra("SizeBoots", sizeBoots);
        startActivity(s);
    }

    public void addAdidas() {
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String name = "adidas_gazelle";
                final Product p = dataSnapshot.child(name).getValue(Product.class);
                Log.d("Product:::", p.toString());
                User login = (User) getIntent().getSerializableExtra("User");
                Log.d("user:::", login.toString());
                login.getCart().add(p);
                p.set_stock(p.get_stock()-1);
                Log.d("user:::", login.toString());
                Toast.makeText(ShoesActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { };
        });
    };

    public void addBoots() {
        products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String name = "Heeled_Boots";
                final Product p = dataSnapshot.child(name).getValue(Product.class);
                Log.d("Product:::", p.toString());
                User login = (User) getIntent().getSerializableExtra("User");
                login.getCart().add(p);
                p.set_stock(p.get_stock()-1);
                products.child(name).setValue(p);
                Log.d("user:::", login.toString());
                Toast.makeText(ShoesActivity.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { };
        });
    };
    }

