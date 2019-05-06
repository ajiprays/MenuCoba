package com.prayitno.aji.menucoba.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Aji} on 3/27/2019.
 */

public class Item implements Parcelable{
    private String name,desc,price;
    private ArrayList<String> image;

    public Item(String name, String desc, ArrayList<String> image,String price) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.price=price;
    }

    public Item() {
    }

    private Item(Parcel in) {
        name = in.readString();
        desc = in.readString();
        image = in.readArrayList(ClassLoader.getSystemClassLoader());
        price = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeList(image);
        dest.writeString(price);
    }
}
