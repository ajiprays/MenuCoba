package com.prayitno.aji.menucoba.database;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.SharedViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Aji} on 4/5/2019.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private SharedViewModel sharedViewModel;
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="Database_App";
    private static final String TABLE_CART="t_cart";
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_PRICE="price";
    private static final String KEY_QUANTITY="quantity";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE="CREATE TABLE "+TABLE_CART+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_PRICE+" TEXT, "+KEY_QUANTITY+" TEXT);";

        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CART);

        onCreate(db);
    }

    public void addToCart(Cart cart){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_NAME,cart.getName());
        contentValues.put(KEY_PRICE,cart.getPrice());
        contentValues.put(KEY_QUANTITY,cart.getQuantity());



        String query="SELECT * FROM "+TABLE_CART+" WHERE "+KEY_NAME+" =?";
        Cursor cursor=this.getReadableDatabase().rawQuery(query,new String[] {cart.getName()});
        Log.i("cursor", String.valueOf(cursor));
        if (cursor.getCount()==0){
            db.insert(TABLE_CART,null,contentValues);
            Log.i("jalan","if");
        }else {
            db.update(TABLE_CART,contentValues,KEY_NAME+" =?",new String[]{cart.getName()});
            Log.i("jalan","else");
        }
        db.close();
    }

    public void updateCart(String name, int quantity,Cart cart){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_NAME,cart.getName());
        contentValues.put(KEY_PRICE,cart.getPrice());
        contentValues.put(KEY_QUANTITY,String.valueOf(quantity));


//        db.update(TABLE_CART,contentValues,KEY_NAME+" =?",new String[]{name});

        String query="SELECT * FROM "+TABLE_CART+" WHERE "+KEY_NAME+" =?";
        Cursor cursor=this.getReadableDatabase().rawQuery(query,new String[] {name});
        Log.i("cursor", String.valueOf(cursor));
        if (cursor.getCount()==0){
            db.insert(TABLE_CART,null,contentValues);
            Log.i("jalan","if");
        }else {
            db.update(TABLE_CART,contentValues,KEY_NAME+" =?",new String[]{name});
            Log.i("jalan","else");
        }
        db.close();
    }

    public List<Cart> showCart(){
        List<Cart> cartList=new ArrayList<>();
        String query="SELECT * FROM "+TABLE_CART;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Cart cart=new Cart();
                cart.setName(cursor.getString(1));
                cart.setPrice(cursor.getString(2));
                cart.setQuantity(cursor.getString(3));
                cartList.add(cart);
            }while (cursor.moveToNext());

        }
        db.close();
        return cartList;
    }

    public void deleteItemCart(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_CART,KEY_NAME+" =?", new String[]{name});
        db.close();
    }

    public void deleteAllCart(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_CART,null,null);
        db.close();
    }
}