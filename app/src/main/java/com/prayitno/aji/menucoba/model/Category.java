package com.prayitno.aji.menucoba.model;

/**
 * Created by ${Aji} on 3/26/2019.
 */

public class Category {
    private String name,picture;

    public Category(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    public Category() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
