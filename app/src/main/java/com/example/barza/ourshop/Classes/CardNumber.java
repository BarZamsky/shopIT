package com.example.barza.ourshop.Classes;

import java.util.Date;

public class CardNumber {

    private String _cardNumber;
    private String _cvv;
    private String _expiry;

    public CardNumber () {}

    public CardNumber (String number, String cvv, String d) {
        this._cardNumber = number;
        this._cvv = cvv;
        this._expiry = d;
    }

    public String getCardNumber() {
        return _cardNumber;
    }

    public String getCVV() { return _cvv; }

    public String getValidation () {return _expiry;}

    public void setCardNumber(String card) {
        this._cardNumber = card;
    }

    public void setCVV(String cvv) {
        this._cvv = cvv;
    }

    public void setExpiry(String exp) {
        this._expiry = exp;
    }

    @Override
    public String toString() {
        return "CardNumber{" +
                "_cardNumber='" + _cardNumber + '\'' +
                ", _cvv=" + _cvv +
                ", _expiry='" + _expiry + '\'' +
                '}';
    }
}
