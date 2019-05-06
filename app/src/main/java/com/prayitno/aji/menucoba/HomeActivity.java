package com.prayitno.aji.menucoba;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.prayitno.aji.menucoba.adapter.CartRecyclerViewAdapter;
import com.prayitno.aji.menucoba.adapter.ViewPagerAdapter;
import com.prayitno.aji.menucoba.database.DatabaseHandler;
import com.prayitno.aji.menucoba.fragment.AboutFragment;
import com.prayitno.aji.menucoba.fragment.BillFragment;
import com.prayitno.aji.menucoba.fragment.CartFragment;
import com.prayitno.aji.menucoba.fragment.MenuFragment;
import com.prayitno.aji.menucoba.fragment.OrderFragment;
import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.DataPass;
import com.prayitno.aji.menucoba.model.SharedViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity{
    TabLayout tabLayout;
    ViewPagerAdapter viewPagerAdapter;
    TextView titleBar;
    ViewPager viewPager;
    private SharedViewModel sharedViewModel;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
        titleBar=findViewById(R.id.title_bar);

        sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel.class);
        databaseHandler=new DatabaseHandler(this);
//        sharedViewModel.setNumber(databaseHandler.showCart().size());

        sharedViewModel.setCartViewmodel(databaseHandler.showCart());
        titleBar.setText("Our Menu");

        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(), this);

        addListFragment();

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
        getCustomView();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                titleBar.setText(viewPagerAdapter.getPageTitle(position));
                Log.i("title", String.valueOf(viewPagerAdapter.getPageTitle(position)));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        Log.i("title ",title);
//        titleBar.setText(title);

    }

    private void addListFragment() {
        viewPagerAdapter.addfragment(new MenuFragment(),"Our Menu");
        viewPagerAdapter.addfragment(new CartFragment(),"Carts");
        viewPagerAdapter.addfragment(new OrderFragment(),"My Orders");
        viewPagerAdapter.addfragment(new BillFragment(),"Bills");
        viewPagerAdapter.addfragment(new AboutFragment(),"About Us");

//        {"Our Menu","Carts","My Orders","Bills","About Us"};
//        fragments.add(new MenuFragment());
//        fragments.add(new CartFragment());
//        fragments.add(new OrderFragment());
//        fragments.add(new BillFragment());
//        fragments.add(new CategoryItemFragment());


    }

    @Override
    protected void onStart(){
        super.onStart();
        sharedViewModel.setCartViewmodel(databaseHandler.showCart());

        Observer<List<Cart>> observer = new Observer<List<Cart>>() {
            @Override
            public void onChanged(@Nullable List<Cart> carts) {
                List<Cart> cartList=carts;
                assert cartList != null;
                DataPass.itemselected=cartList.size();

            }
        };
        sharedViewModel.getCart().observe(this,observer);

    }

    private void getCustomView() {
        for (int i=0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(viewPagerAdapter.getTabView(i));
        }
    }
    public void setSharedViewModel(){
        sharedViewModel.setCartViewmodel(databaseHandler.showCart());
    }
}
