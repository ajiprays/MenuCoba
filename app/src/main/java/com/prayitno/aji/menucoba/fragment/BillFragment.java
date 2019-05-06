package com.prayitno.aji.menucoba.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prayitno.aji.menucoba.R;
import com.prayitno.aji.menucoba.model.Order;

import java.text.DecimalFormat;

public class BillFragment extends Fragment {
    private LinearLayout content;
    private TextView tax,total;
    private TextView norow,namerow,pricerow,totalrow,quantityrow;
    private DatabaseReference dbRef;
    private TableRow tableRow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bill, container, false);
        content=view.findViewById(R.id.content);
        tax=view.findViewById(R.id.taxtxt);
        total=view.findViewById(R.id.totaltxt);

        dbRef= FirebaseDatabase.getInstance().getReference();

        getOrderByStatus();

        return view;
    }

    private void getOrderByStatus() {
        String format="###,###.##";
        final DecimalFormat dm=new DecimalFormat(format);

        dbRef.child("Orders").child("table_1").orderByChild("status").equalTo("served").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int totalamount=0;
                int i=1;
                content.removeAllViews();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    Order order=ds.getValue(Order.class);
                    tableRow= (TableRow) LayoutInflater.from(getActivity()).inflate(R.layout.table_row_bill,null);
                    norow=tableRow.findViewById(R.id.table_no);
                    namerow=tableRow.findViewById(R.id.table_name);
                    pricerow=tableRow.findViewById(R.id.table_price);
                    totalrow=tableRow.findViewById(R.id.table_total);
                    quantityrow=tableRow.findViewById(R.id.table_quantity);

                    norow.setText(String.valueOf(i));
                    namerow.setText(order.getName());
                    quantityrow.setText(order.getQuantity());
                    pricerow.setText("Rp. "+dm.format(Integer.parseInt(order.getPrice())));
                    int subtotal=Integer.parseInt(order.getPrice())*Integer.parseInt(order.getQuantity());
                    totalamount=totalamount+subtotal;
                    totalrow.setText("Rp. "+dm.format(subtotal));

                    content.addView(tableRow);
                    i++;

                }
                int tx=totalamount*15/100;
                int totalwithtx= totalamount+tx;
                tax.setText("Rp. "+dm.format(tx));
                total.setText("Rp. "+dm.format(totalwithtx));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
