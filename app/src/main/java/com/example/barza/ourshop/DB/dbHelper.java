package com.example.barza.ourshop.DB;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.barza.ourshop.Classes.User;
import com.example.barza.ourshop.RegistrationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.barza.ourshop.Classes.Product;
import com.google.firebase.database.ValueEventListener;

public class dbHelper {
    FirebaseDatabase db;
    DatabaseReference users, products;
    private User _user;

    public dbHelper() {}

    public void addProduct (Product p) {
        db = FirebaseDatabase.getInstance();
        products = db.getReference("Products");
        products.child(p.get_name()).setValue(p);
    }

    public void getUserFromDB (final String email) {
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               _user =  dataSnapshot.child(email.replace(".", "|")).getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

//    private interface myCallBack {
//        void onCallBack(User user);
//    }
    public User getUser() {
        return _user;
    }

}
