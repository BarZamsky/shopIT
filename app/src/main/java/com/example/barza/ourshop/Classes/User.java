package com.example.barza.ourshop.Classes;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class User implements Serializable{

    private static String _id ;
    private String _email;
    private String _password;
    private String _name;
    private String _lastName;
    private ArrayList<Product> _cart = new ArrayList<>();

    public User() {}

    public User(String email, String password, String name, String lastName) {
        this._email = email;
        this._password = password;
        this._name = name;
        this._lastName = lastName;
        this._id = UUID.randomUUID().toString();
    }

    public String getEmail() {
        return _email;
    }

    public String getLastName() {
        return _lastName;
    }

    public String getID () {return _id;}

    public String getName() { return _name; }

    public String getPassword() {
        return _password;
    }

    public void setName(String name) {
        this._name = name;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public List<Product> getCart() {
        return _cart;
    }

    public void setCart(ArrayList<Product> _cart) {
        this._cart = _cart;
    }

    @Override
    public String toString() {
        return "User{" +
                "_email='" + _email + '\'' +
                ", _password='" + _password + '\'' +
                ", _name='" + _name + '\'' +
                ", _lastName='" + _lastName + '\'' +
                ", _cart=" + _cart +
                '}';
    }
}
