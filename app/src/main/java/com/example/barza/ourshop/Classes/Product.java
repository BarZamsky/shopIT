package com.example.barza.ourshop.Classes;

import android.media.Image;

import java.io.Serializable;

public class Product implements Serializable {

    String _name;
    String _description;
    double _price;
    int _stock;

    public Product() {}

    public Product(String n, String d, double p, int s) {
        this._description = d;
        this._name = n;
        this._price = p;
        this._stock = s;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public double get_price() {
        return _price;
    }

    public void set_price(int _price) {
        this._price = _price;
    }

    public int get_stock() {
        return _stock;
    }

    public void set_stock(int _stock) {
        this._stock = _stock;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "_name='" + _name + '\'' +
                ", _description='" + _description + '\'' +
                ", _price=" + _price +
                ", _stock=" + _stock +
                '}';
    }
}
