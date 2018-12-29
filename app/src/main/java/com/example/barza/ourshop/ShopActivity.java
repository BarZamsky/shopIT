package com.example.barza.ourshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.barza.ourshop.Classes.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShopActivity extends AppCompatActivity {

    Button btnSkirt, btnJeans, btnShirts, btnShoes;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        btnShoes = (Button) findViewById(R.id.btnShoes);
        btnJeans = (Button) findViewById(R.id.btnJeans);
        btnShirts = (Button) findViewById(R.id.btnShirts);
        btnSkirt = (Button) findViewById(R.id.btnSkirts);
        img = (ImageView) findViewById(R.id.btnCart);

        btnShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShoes();
            }
        });

        btnJeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickJeans();
            }
        });

        btnShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShirts();
            }
        });

        btnSkirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSkirts();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCart();
            }
        });

    }
    public void onClickShoes() {
        Intent s = new Intent(getApplicationContext(), ShoesActivity.class);
        String email = getIntent().getStringExtra("email");
        s.putExtra("email", email);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        startActivity(s);
    }

    public void onClickJeans() {
        Intent s = new Intent(getApplicationContext(), JeansActivity.class);
        String email = getIntent().getStringExtra("email");
        s.putExtra("email", email);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        startActivity(s);
    }
    public void onClickShirts() {
        Intent s = new Intent(getApplicationContext(), ShirtsActivity.class);
        String email = getIntent().getStringExtra("email");
        s.putExtra("email", email);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        startActivity(s);
    }
    public void onClickSkirts() {
        Intent s = new Intent(getApplicationContext(), SkirtsActivity.class);
        String email = getIntent().getStringExtra("email");
        s.putExtra("email", email);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        startActivity(s);
    }

    public void onClickCart() {
        Intent s = new Intent(getApplicationContext(), CartActivity.class);
        String email = getIntent().getStringExtra("email");
        s.putExtra("email", email);
        User login = (User) getIntent().getSerializableExtra("User");
        s.putExtra("User", login);
        String sizeAdidas = getIntent().getStringExtra("SizeAdidas");
        s.putExtra("SizeAdidas", sizeAdidas);
        String sizeBoots = getIntent().getStringExtra("SizeBoots");
        s.putExtra("SizeBoots", sizeBoots);
        startActivity(s);
    }
}
