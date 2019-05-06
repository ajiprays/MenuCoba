package com.prayitno.aji.menucoba.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prayitno.aji.menucoba.Interface.ItemClickLinstener;
import com.prayitno.aji.menucoba.R;

/**
 * Created by ${Aji} on 3/26/2019.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView nametext;
    public ImageView imageView;
    public ItemClickLinstener listener;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        nametext=itemView.findViewById(R.id.category_name);
        imageView=itemView.findViewById(R.id.image_category);
    }
    public void setItemClickListener(ItemClickLinstener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {

    }
}
