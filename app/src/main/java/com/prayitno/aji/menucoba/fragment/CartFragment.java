package com.prayitno.aji.menucoba.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.adapter.CartRecyclerViewAdapter;
import com.prayitno.aji.menucoba.database.DatabaseHandler;
import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.DataPass;
import com.prayitno.aji.menucoba.model.SharedViewModel;

import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartRecyclerViewAdapter adapter;
    private DatabaseHandler databaseHandler;
    private List<Cart> cartList;
    private TextView item_selected,nocart;
    private SharedViewModel sharedViewModel;
    private Button btn_order;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        nocart=view.findViewById(R.id.nocart);
        item_selected=view.findViewById(R.id.item_selected);
        btn_order=view.findViewById(R.id.btn_order);

//        if (DataPass.currentcart!=null) {
//            nocart.setVisibility(View.GONE);
//            Log.i("cart", DataPass.currentcart.getArrayList().get(0).toString());
//            Log.i("size", String.valueOf(DataPass.currentcart.getArrayList().size()));
//            adapter=new CartRecyclerViewAdapter(DataPass.currentcart.getArrayList());
//            recyclerView.setAdapter(adapter);
//
//        }else {
//            nocart.setVisibility(View.VISIBLE);
//        }
        sharedViewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        databaseHandler=new DatabaseHandler(getActivity());
//        cartList= databaseHandler.showCart();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderNow();
                sharedViewModel.setCartViewmodel(databaseHandler.showCart());
            }
        });

//        if (cartList.size()==0){
//            nocart.setVisibility(View.VISIBLE);
//            item_selected.setVisibility(View.GONE);
//        }
//        if (DataPass.itemselected==0){
//            nocart.setVisibility(View.VISIBLE);
//            item_selected.setVisibility(View.GONE);
//        }else {
//            Log.d("cart", String.valueOf(cartList));
//            nocart.setVisibility(View.GONE);
//            item_selected.setVisibility(View.VISIBLE);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerView.setHasFixedSize(true);
//            adapter=new CartRecyclerViewAdapter(getActivity(),recyclerView,item_selected,cartList,sharedViewModel);
//            recyclerView.setAdapter(adapter);
//        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);

        Observer<List<Cart>> observer = new Observer<List<Cart>>() {
            @Override
            public void onChanged(@Nullable List<Cart> carts) {
                cartList=carts;
                assert cartList != null;
//                DataPass.itemselected=cartList.size();
                Log.i("change","Change");
                if (cartList==null || cartList.size()==0){
                    Log.i("cartlist","null");
                    nocart.setVisibility(View.VISIBLE);
                    item_selected.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    btn_order.setVisibility(View.GONE);
                }else {
                    Log.i("cartlist","notnull");
                    nocart.setVisibility(View.GONE);
                    item_selected.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    btn_order.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setHasFixedSize(true);
                    adapter=new CartRecyclerViewAdapter(getActivity(), item_selected,cartList,sharedViewModel);
                    recyclerView.setAdapter(adapter);
                }
            }
        };
        sharedViewModel.getCart().observe(this,observer);

    }

    private void orderNow() {
        DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        String table_no="table_1";
        sharedViewModel.setCartViewmodel(databaseHandler.showCart());
        List<Cart> dbfromcart = databaseHandler.showCart();

        Log.i("size", String.valueOf(dbfromcart.size()));
        for (Cart cart:dbfromcart){
            final HashMap<String,Object> orderMap=new HashMap<>();
            orderMap.put("name",cart.getName());
            orderMap.put("price",cart.getPrice());
            orderMap.put("quantity",cart.getQuantity());
            orderMap.put("status","waiting");

            orderRef.child(table_no).child(cart.getName()).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.i("map", String.valueOf(orderMap));
                }
            });
        }
//        for (int i=0;i<dbfromcart.size();i++){
//            final HashMap<String,Object> orderMap=new HashMap<>();
//            orderMap.put("name",dbfromcart.get(i).getName());
//            orderMap.put("price",dbfromcart.get(i).getPrice());
//            orderMap.put("quantity",dbfromcart.get(i).getQuantity());
//            orderMap.put("status","waiting");
//
//            orderRef.child(table_no).child(dbfromcart.get(i).getName()).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    Log.i("map", String.valueOf(orderMap));
//                }
//            });
//        }
        databaseHandler.deleteAllCart();
        Toast.makeText(getActivity(),"Order Success",Toast.LENGTH_SHORT).show();
        Log.i("sizeafterdelete", String.valueOf(databaseHandler.showCart()));
    }
}
