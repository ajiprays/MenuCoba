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

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name,price;
    public ImageView imageView;
    public Button btn_add;
    public ItemClickLinstener listener;
    public ElegantNumberButton btn_number;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.item_name);
        price=itemView.findViewById(R.id.item_price);
        imageView=itemView.findViewById(R.id.image_item);
        btn_add=itemView.findViewById(R.id.btn_add);
        btn_number=itemView.findViewById(R.id.btn_number);
    }
    public void setItemClickListener(ItemClickLinstener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {

    }
}
