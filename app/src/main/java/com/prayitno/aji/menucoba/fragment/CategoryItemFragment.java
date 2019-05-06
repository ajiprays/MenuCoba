package com.prayitno.aji.menucoba.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prayitno.aji.menucoba.MenuItemActivity;
import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.database.DatabaseHandler;
import com.prayitno.aji.menucoba.model.ArrayListModel;
import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.DataPass;
import com.prayitno.aji.menucoba.model.Item;
import com.prayitno.aji.menucoba.model.SharedViewModel;
import com.prayitno.aji.menucoba.viewholder.ItemViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CategoryItemFragment extends Fragment implements CategoryItemFragmentInterface {
    private String cat_name;
    private RecyclerView recyclerView;
    private TextView item_selected,no_item;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private Cart cartmodel;
    private ArrayList cart=new ArrayList<>();
    private ArrayListModel arrayListModel=new ArrayListModel();
    private DatabaseHandler databaseHandler;
    private ProgressDialog progressDialog;
    private SharedViewModel sharedViewModel;
    private DecimalFormat dm;

    public CategoryItemFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_category_item, container, false);
        TextView itemlist = view.findViewById(R.id.item_list);
        no_item=view.findViewById(R.id.noItem);
        item_selected=view.findViewById(R.id.item_selected);
        Button btnBack = view.findViewById(R.id.btnBack);
        recyclerView=view.findViewById(R.id.recycler_view);
        progressDialog=new ProgressDialog(getActivity());

        String format="###,###.##";
        dm=new DecimalFormat(format);

        sharedViewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        databaseHandler=new DatabaseHandler(getActivity());
        if ( getArguments().getString("cat_name")!=null){

            cat_name=getArguments().getString("cat_name");
            Log.i("bundle", cat_name);
            itemlist.setText(cat_name);
            getItemFromCategory();
        }

//        item_selected.setText(String.valueOf(cartsize.size())+" Item Selected");
        item_selected.setText(String.valueOf(DataPass.itemselected)+" Item Selected");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft=getParentFragment().getChildFragmentManager().beginTransaction();
                ft.remove(CategoryItemFragment.this);
                ft.commit();
                Log.i("remove", String.valueOf(getParentFragment()));
                ((MenuFragment)getParentFragment()).setContainer();


            }
        });

        Observer<List<Cart>> observer=new Observer<List<Cart>>() {
            @Override
            public void onChanged(@Nullable List<Cart> carts) {
                item_selected.setText(String.valueOf(DataPass.itemselected)+" Item Selected");
            }
        };

        sharedViewModel.getCart().observe(this,observer);

        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    private void getItemFromCategory(){

        progressDialog.setMessage("Loading Item...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        FirebaseRecyclerOptions<Item> options=new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(databaseReference.child("menus").orderByChild("category").equalTo(cat_name),Item.class)
                .build();
        Log.i("option",options.toString());
        if (options.getSnapshots().equals(null)) {
            recyclerView.removeAllViews();
            no_item.setVisibility(View.VISIBLE);
        }
        else {
            no_item.setVisibility(View.GONE);
            FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position, @NonNull final Item model) {

                            holder.name.setText(model.getName());
                            holder.price.setText("Rp. "+dm.format(Integer.parseInt(model.getPrice())));

                            if (model.getImage()==null){
                                holder.imageView.setImageResource(R.mipmap.ic_launcher);
                            }else {
                                Glide.with(getActivity()).load(model.getImage().get(0)).into(holder.imageView);
                            }

                            holder.btn_number.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                                @Override
                                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                                    cartmodel = new Cart();
                                    cartmodel.setName(model.getName());
                                    cartmodel.setPrice(model.getPrice());
                                    databaseHandler.updateCart(model.getName(), newValue,cartmodel);
                                    sharedViewModel.setCartViewmodel(databaseHandler.showCart());
                                    notifyDataSetChanged();
                                }
                            });

                            holder.btn_add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    Log.i("number onclick", holder.btn_number.getNumber());
//                                    AddToCart(model.getName(),model.getPrice(),holder.btn_number.getNumber());
                                    cartmodel = new Cart();
                                    cartmodel.setName(model.getName());
                                    cartmodel.setPrice(model.getPrice());
                                    cartmodel.setQuantity("1");
                                    databaseHandler.addToCart(cartmodel);
                                    holder.btn_add.setVisibility(View.GONE);
                                    holder.btn_number.setVisibility(View.VISIBLE);
                                    notifyDataSetChanged();

                                    sharedViewModel.setCartViewmodel(databaseHandler.showCart());
//                                    item_selected.setText(String.valueOf(cartsize.size())+" Item Selected");
//                                    item_selected.setText(String.valueOf(DataPass.itemselected)+" Item Selected");

                                }
                            });
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), MenuItemActivity.class);
                                    intent.putExtra("menu_item",model);
                                    startActivity(intent);

                                }
                            });
                        }

                        @NonNull
                        @Override
                        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_list, parent, false);
                            cartmodel = new Cart();
                            Log.i("cartmodel", cartmodel.toString());

                            return new ItemViewHolder(view);
                        }
                    };
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
            adapter.startListening();
        }
    }
    @Override
    public void AddToCart(String name, String price, String quantity) {
        cartmodel = new Cart();
        cartmodel.setName(name);
        cartmodel.setPrice(price);
        cartmodel.setQuantity(quantity);
        Log.i("cart", cartmodel.toString());
        cart.add(cartmodel);
        arrayListModel.setArrayList(cart);
        DataPass.currentcart = arrayListModel;
        Log.i("isicart", DataPass.currentcart.getArrayList().toString());
    }

}
