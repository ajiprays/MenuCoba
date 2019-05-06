package com.prayitno.aji.menucoba.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.database.DatabaseHandler;
import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.DataPass;
import com.prayitno.aji.menucoba.model.SharedViewModel;
import com.prayitno.aji.menucoba.viewholder.CartViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ${Aji} on 3/28/2019.
 */

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Cart> cart;
    private DatabaseHandler databaseHandler;
    private SharedViewModel sharedViewModel;
    private TextView item_selected;
    private DecimalFormat dm;

    public CartRecyclerViewAdapter(Context context, TextView item_selected, List<Cart> cart, SharedViewModel sharedViewModel){
        this.cart=cart;
        this.sharedViewModel=sharedViewModel;
        databaseHandler=new DatabaseHandler(context);
//        cart=databaseHandler.showCart();
        this.item_selected=item_selected;
        //        item_selected.setText(String.valueOf(size)+" Item Selected");
        DataPass.itemselected= cart.size();
        item_selected.setText(String.valueOf(DataPass.itemselected)+" Item Selected");
        String format="###,###.##";
        dm=new DecimalFormat(format);

    }
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_list,parent,false);

        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, int position) {
        holder.name.setText(cart.get(position).getName());
        holder.price.setText("Rp. "+dm.format(Integer.parseInt(cart.get(position).getPrice())));
        holder.quantity.setText(cart.get(position).getQuantity());
        Log.i("test",cart.toString());
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                databaseHandler.deleteItemCart(cart.get(position).getName());
                databaseHandler.deleteItemCart(cart.get(holder.getAdapterPosition()).getName());
//                cart=databaseHandler.showCart();
//                item_selected.setText(String.valueOf(cart.size())+" Item Selected");
                DataPass.itemselected=cart.size();
                item_selected.setText(String.valueOf(DataPass.itemselected)+" Item Selected");
                sharedViewModel.setCartViewmodel(databaseHandler.showCart());
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

}
