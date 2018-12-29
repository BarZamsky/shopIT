package com.example.barza.ourshop.Classes;

public class Order {

    private String _orderNumber;
    private boolean _isApproved;

    public Order() {}

    public Order(String number, boolean flag) {
        this._orderNumber = number;
        this._isApproved = flag;
    }

    public String get_orderNumber() {
        return _orderNumber;
    }

    public void set_orderNumber(String _orderNumber) {
        this._orderNumber = _orderNumber;
    }

    public boolean is_isApproved() {
        return _isApproved;
    }

    public void set_isApproved(boolean _isApproved) {
        this._isApproved = _isApproved;
    }

    @Override
    public String toString() {
        return "Order{" +
                "_orderNumber='" + _orderNumber + '\'' +
                ", _isApproved=" + _isApproved +
                '}';
    }
}
