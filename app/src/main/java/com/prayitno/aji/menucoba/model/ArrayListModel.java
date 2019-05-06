package com.prayitno.aji.menucoba.model;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ${Aji} on 3/28/2019.
 */

public class ArrayListModel {
    private ArrayList arrayList;

    public ArrayListModel(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayListModel() {
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }
}
