package com.prayitno.aji.menucoba.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.prayitno.aji.menucoba.Interface.ItemClickLinstener;
import com.prayitno.aji.menucoba.R;

/**
 * Created by ${Aji} on 3/26/2019.
 */

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name,price,quantity;
    public ImageView btn_remove;
    public ItemClickLinstener listener;

    public CartViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.item_name);
        price=itemView.findViewById(R.id.item_price);
        quantity=itemView.findViewById(R.id.item_quantity);
        btn_remove=itemView.findViewById(R.id.remove_item);
    }
    public void setItemClickListener(ItemClickLinstener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {

    }
}
