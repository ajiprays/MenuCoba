package com.prayitno.aji.menucoba.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ${Aji} on 3/30/2019.
 */

public class CartParcel implements Parcelable {
    private String name,price,quantity;

    public CartParcel() {
    }

    public CartParcel(String name, String price, String quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public static Creator<CartParcel> getCREATOR() {
        return CREATOR;
    }

    protected CartParcel(Parcel in) {
        name=in.readString();
        price=in.readString();
        quantity=in.readString();
    }

    public static final Creator<CartParcel> CREATOR = new Creator<CartParcel>() {
        @Override
        public CartParcel createFromParcel(Parcel in) {
            return new CartParcel(in);
        }

        @Override
        public CartParcel[] newArray(int size) {
            return new CartParcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(quantity);
    }
}
