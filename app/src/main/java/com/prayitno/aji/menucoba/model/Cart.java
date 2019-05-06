package com.prayitno.aji.menucoba.model;

import android.os.Parcel;

import java.util.ArrayList;

/**
 * Created by ${Aji} on 3/27/2019.
 */

public class Cart extends CartParcel {
    private String name,price,quantity;

    public Cart(String name,String price,String quantity) {
        this.name = name;
        this.quantity = quantity;
        this.price=price;
    }

    public Cart(Parcel in, String name, String price, String quantity) {
        super(in);
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Cart() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
