package com.example.barza.ourshop.SERVER;

import com.example.barza.ourshop.Classes.Product;
import com.example.barza.ourshop.Classes.User;
import com.example.barza.ourshop.DB.dbHelper;
import com.google.firebase.database.FirebaseDatabase;

public class Server {
    private dbHelper _dbH;

    public Server () {}

    public void addProduct(Product p) {
        _dbH.addProduct(p);
    }

    public void callUserDB(String email) {
        _dbH.getUserFromDB(email);
    }

    public User getUser() {
      return  _dbH.getUser();
    }
}

