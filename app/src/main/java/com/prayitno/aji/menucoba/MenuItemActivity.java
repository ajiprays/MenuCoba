package com.prayitno.aji.menucoba;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.prayitno.aji.menucoba.database.DatabaseHandler;
import com.prayitno.aji.menucoba.fragment.MenuFragment;
import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.Item;
import com.prayitno.aji.menucoba.model.SharedViewModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DecimalFormat;

public class MenuItemActivity extends AppCompatActivity {
    private Item menu_item;
    private DatabaseHandler databaseHandler;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        databaseHandler=new DatabaseHandler(this);

        TextView text_name=findViewById(R.id.menu_name);
        TextView text_desc=findViewById(R.id.menu_desc);
        TextView text_price=findViewById(R.id.menu_price);
        Button btn_back=findViewById(R.id.btn_back);

//        ImageView imageView=findViewById(R.id.menu_image);
        CarouselView carouselView = findViewById(R.id.carouselView);

        sharedViewModel=ViewModelProviders.of(this).get(SharedViewModel.class);

        String format="###,###.##";
        DecimalFormat dm=new DecimalFormat(format);
        text_desc.setMovementMethod(new ScrollingMovementMethod());

        final ElegantNumberButton numberButton=findViewById(R.id.btn_number);
        Button buttonAdd=findViewById(R.id.btn_add);

        menu_item=getIntent().getParcelableExtra("menu_item");

        text_name.setText(menu_item.getName());
        text_desc.setText(menu_item.getDesc());
        text_price.setText("Rp. "+dm.format(Integer.parseInt(menu_item.getPrice())));
//        Glide.with(this).load(menu_item.getImage()).into(imageView);
        if (menu_item.getImage()!=null){
            carouselView.setPageCount(menu_item.getImage().size());
            carouselView.setImageListener(new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    Glide.with(getApplicationContext()).load(menu_item.getImage().get(position)).into(imageView);
                }
            });
        }


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cartmodel = new Cart();
                cartmodel.setName(menu_item.getName());
                cartmodel.setPrice(menu_item.getPrice());
                cartmodel.setQuantity(numberButton.getNumber());
                databaseHandler.addToCart(cartmodel);
//                sharedViewModel.setCartViewmodel(databaseHandler.showCart());
                Toast.makeText(getApplicationContext(),"Added To Cart",Toast.LENGTH_SHORT).show();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}
