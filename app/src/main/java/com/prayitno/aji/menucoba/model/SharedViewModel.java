package com.prayitno.aji.menucoba.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

/**
 * Created by ${Aji} on 5/3/2019.
 */

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> number = new MutableLiveData<>();
    private MutableLiveData<List<Cart>> cartViewmodel=new MutableLiveData<>();

    public LiveData<List<Cart>> getCart(){

        return cartViewmodel;
    }
    public  void setCartViewmodel(List<Cart> cart){
        cartViewmodel.setValue(cart);
    }

    public LiveData<Integer> getNumber(){

        return number;
    }
    public void setNumber(Integer mnumber){
        number.setValue(mnumber);
    }

//    private void loadNumber(){
//        DatabaseHandler databaseHandler=new DatabaseHandler();
//
//        itemSelected.setValue(databaseHandler.showCart().size());
//    }
}
