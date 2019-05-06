package com.prayitno.aji.menucoba.model;

/**
 * Created by ${Aji} on 4/9/2019.
 */

public class Order {
    private String name,quantity,status,price;

    public Order(String name, String quantity, String status,String price) {
        this.name = name;
        this.quantity = quantity;
        this.status = status;
        this.price=price;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
