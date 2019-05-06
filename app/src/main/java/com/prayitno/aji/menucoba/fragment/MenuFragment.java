package com.prayitno.aji.menucoba.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.prayitno.aji.menucoba.MenuItemActivity;
import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.database.DatabaseHandler;
import com.prayitno.aji.menucoba.model.Cart;
import com.prayitno.aji.menucoba.model.Category;
import com.prayitno.aji.menucoba.model.DataPass;
import com.prayitno.aji.menucoba.model.Item;
import com.prayitno.aji.menucoba.model.SharedViewModel;
import com.prayitno.aji.menucoba.viewholder.CategoryViewHolder;
import com.prayitno.aji.menucoba.viewholder.ItemViewHolder;

import java.util.List;


public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    public FrameLayout content_container;
    private RelativeLayout relativeLayout;
    private TextView item_selected;
    private Button btn_search,btn_close;
    private EditText edit_search;
//    private ArrayList<Category> category = new ArrayList<>();
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private DatabaseHandler databaseHandler;
    private ProgressDialog progressDialog;
    private SharedViewModel sharedViewModel;

    public MenuFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        relativeLayout=view.findViewById(R.id.relatif_menu);
        content_container=view.findViewById(R.id.container_item);
        recyclerView = view.findViewById(R.id.recycler_view);
        item_selected=view.findViewById(R.id.item_selected);
        btn_search=view.findViewById(R.id.btn_search);
        btn_close=view.findViewById(R.id.btn_back);
        edit_search=view.findViewById(R.id.edit_search);
        progressDialog=new ProgressDialog(getActivity());
        sharedViewModel= ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        relativeLayout.setVisibility(View.VISIBLE);
        content_container.setVisibility(View.GONE);

        databaseHandler=new DatabaseHandler(getActivity());
        getCategory();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_search.setVisibility(View.VISIBLE);
                btn_close.setVisibility(View.VISIBLE);
                btn_search.setVisibility(View.GONE);
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategory();
                edit_search.setVisibility(View.GONE);
                btn_close.setVisibility(View.GONE);
                btn_search.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        databaseReference= FirebaseDatabase.getInstance().getReference();
//        List<Cart> cart=databaseHandler.showCart();
//        item_selected.setText(String.valueOf(cart.size())+" Item Selected");

        Observer<List<Cart>> observer=new Observer<List<Cart>>() {
            @Override
            public void onChanged(@Nullable List<Cart> carts) {
                Log.i("menufragment","observer");
                item_selected.setText(String.valueOf(DataPass.itemselected)+" Item Selected");
            }
        };

        sharedViewModel.getCart().observe(this,observer);

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    getCategory();
                }else{
                    progressDialog.setMessage("Searching Item...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    searchItem(s.toString().toLowerCase());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i("onpause","onpause");
    }

    private void searchItem(String s) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Query query=databaseReference.child("menus").orderByKey()
                .startAt(s)
                .endAt(s+"\uf0ff");
//
//        Query query=databaseReference.child("menus").child("").orderByKey()
//                .startAt(s)
//                .endAt(s+"\uf0ff");

        FirebaseRecyclerOptions<Item> options=new FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class)
                .build();

        Log.i("options", options.getSnapshots().toString());
        FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position, @NonNull final Item model) {

                        holder.name.setText(model.getName());
                        holder.price.setText("Rp. "+model.getPrice());
                        if (model.getImage()==null){
                            holder.imageView.setImageResource(R.mipmap.ic_launcher);
                        }else {
                            Glide.with(getActivity()).load(model.getImage().get(0)).into(holder.imageView);
                        }

                        holder.btn_number.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                            @Override
                            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                                Cart cartmodel = new Cart();
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
                                Cart cartmodel = new Cart();
                                cartmodel.setName(model.getName());
                                cartmodel.setPrice(model.getPrice());
                                cartmodel.setQuantity("1");
                                databaseHandler.addToCart(cartmodel);
                                holder.btn_add.setVisibility(View.GONE);
                                holder.btn_number.setVisibility(View.VISIBLE);
                                sharedViewModel.setCartViewmodel(databaseHandler.showCart());
                                notifyDataSetChanged();
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
                        Cart cartmodel = new Cart();
                        Log.i("cartmodel", cartmodel.toString());

                        return new ItemViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
        adapter.startListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        getCategory();
    }

    private void getCategory(){

        progressDialog.setMessage("Loading Category...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        FirebaseRecyclerOptions<Category> options=new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(databaseReference.child("category"),Category.class)
                .build();

        FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter=
                new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position, @NonNull final Category model) {
                        holder.nametext.setText(model.getName());
                        if (model.getPicture()==null){
                            holder.imageView.setImageResource(R.mipmap.ic_launcher);
                        }else {
                            Glide.with(getActivity()).load(model.getPicture()).into(holder.imageView);
                        }
                        Log.i("info",model.getPicture());
                        Log.i("infoholder",holder.nametext.getText().toString());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                model.getName();
                                relativeLayout.setVisibility(View.GONE);
                                content_container.setVisibility(View.VISIBLE);
                                Bundle bundle=new Bundle();
                                bundle.putString("cat_name",model.getName());
                                Fragment catItemFrag=new CategoryItemFragment();
                                catItemFrag.setArguments(bundle);

                                FragmentTransaction ft=getChildFragmentManager().beginTransaction();
                                ft.add(R.id.container_item,catItemFrag);
                                ft.commit();
                                Log.i("remove", this.toString());
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(getActivity()).inflate(R.layout.category_item_list,parent,false);

                        return new CategoryViewHolder(view);
                    }
                };

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
        adapter.startListening();
    }

    public void setContainer(){
        relativeLayout.setVisibility(View.VISIBLE);
        content_container.setVisibility(View.GONE);
    }

}