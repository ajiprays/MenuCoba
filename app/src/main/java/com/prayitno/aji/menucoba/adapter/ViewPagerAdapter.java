package com.prayitno.aji.menucoba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.fragment.AboutFragment;
import com.prayitno.aji.menucoba.fragment.BillFragment;
import com.prayitno.aji.menucoba.fragment.CartFragment;
import com.prayitno.aji.menucoba.fragment.CategoryItemFragment;
import com.prayitno.aji.menucoba.fragment.MenuFragment;
import com.prayitno.aji.menucoba.fragment.OrderFragment;

import java.util.ArrayList;

/**
 * Created by ${Aji} on 3/26/2019.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private int [] homeicon=new int[]{R.drawable.menu_icon,R.drawable.cart_icon,R.drawable.order_icon,R.drawable.bills,R.drawable.about_icon};

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ViewPagerAdapter(FragmentManager supportFragmentManager,Context context) {
        super(supportFragmentManager);

        this.mContext=context;
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
//        Fragment frag=null;
//        Bundle bundle=new Bundle();
//        if (position==0){
//            frag=new MenuFragment();
//        }else if (position==1){
//            frag=new CartFragment();
//        }else if (position==2){
//            frag=new OrderFragment();
//        }else if (position==3){
//            frag=new BillFragment();
//        }else if (position==4){
//            frag=new CategoryItemFragment();
//        }
//        frag.setArguments(bundle);
//        return frag;
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {

        return titles.get(position);
    }

    public View getTabView(int position){
        View tab= LayoutInflater.from(mContext).inflate(R.layout.custom_tab_view,null);
        TextView textView= tab.findViewById(R.id.tab_view_text);
        ImageView imageView= tab.findViewById(R.id.menu_image);
        textView.setText(titles.get(position));
        imageView.setImageResource(homeicon[position]);

        return tab;
    }
//    public View onSelected(int position){
//        View tab= LayoutInflater.from(mContext).inflate(R.layout.custom_tab_view,null);
//        TextView textView= tab.findViewById(R.id.tab_view_text);
//        ImageView imageView= tab.findViewById(R.id.menu_image);
//        textView.setTextColor(Color.parseColor(String.valueOf(R.color.colorGrey)));
//        tab.setBackgroundColor(Color.parseColor(String.valueOf(R.color.colorPrimary)));
//
//        return tab;
//    }
    public void addfragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

}
