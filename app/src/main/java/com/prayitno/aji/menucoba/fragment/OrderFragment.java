package com.prayitno.aji.menucoba.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.model.Order;

import java.text.DecimalFormat;

public class OrderFragment extends Fragment {
//    private List<Order> orders=new ArrayList<>();;
    private TableLayout tableLayout;
    private TableRow tableRow;
    private TextView no;
    private TextView name;
    private TextView price;
    private TextView status;
    private TextView quantity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order, container, false);

        tableLayout=view.findViewById(R.id.tablelayoutorder);

        getItemOrder();

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();


    }

    private void getItemOrder() {
        DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Orders").child("table_1");

        String format="###,###.##";
        final DecimalFormat dm=new DecimalFormat(format);

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableLayout.removeAllViews();
                int i=1;

                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Order order=ds.getValue(Order.class);
//                    orders.add(order);
                    Log.i("order",ds.getValue().toString());

                    tableRow= (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.table_row_order,null);
                    no=tableRow.findViewById(R.id.table_no);
                    name=tableRow.findViewById(R.id.table_name);
                    price=tableRow.findViewById(R.id.table_price);
                    status=tableRow.findViewById(R.id.table_status);
                    quantity=tableRow.findViewById(R.id.table_quantity);

                    no.setText(String.valueOf(i));
                    assert order != null;
                    name.setText(order.getName());
                    price.setText("Rp. "+dm.format(Integer.parseInt(order.getPrice())));
                    status.setText(order.getStatus());
                    quantity.setText(order.getQuantity());
                    tableLayout.addView(tableRow);
                    i++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
