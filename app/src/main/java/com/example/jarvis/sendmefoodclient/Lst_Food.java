package com.example.jarvis.sendmefoodclient;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.MenuHld.LstFoodHld;
import com.example.jarvis.sendmefoodclient.MenuHld.OrderDetailAdapter;
import com.example.jarvis.sendmefoodclient.Model.Orders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class Lst_Food extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Orders,LstFoodHld> adapter;

    FirebaseDatabase db;
    DatabaseReference db_ref;

    MaterialSpinner mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lst__food);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("Requests").child(Current.currentRqData.getKey()).child("orders");

        recyclerView = (RecyclerView) findViewById(R.id.foodLst);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getOrderList();
    }

    private void getOrderList() {
        adapter = new FirebaseRecyclerAdapter<Orders, LstFoodHld>(
                Orders.class,
                R.layout.order_detail_list,
                LstFoodHld.class,
                db_ref.orderByChild("owner").equalTo(Current.usrCurrent.getNumber())
                ) {
            @Override
            protected void populateViewHolder(LstFoodHld viewHolder, Orders model, int position) {
                viewHolder.Price.setText(String.format("Price: $%s",model.getPrice()));
                viewHolder.Qantity.setText(String.format("Quantity: %s",model.getQntity()));
                viewHolder.Status.setText(String.format("Status: %s",statusCode(model.getStatus())));
                viewHolder.Name.setText(model.getProdName());
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().equals("Update")){
            updtDialg(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);
    }


    private void updtDialg(String key, final Orders item) {
            final AlertDialog.Builder alertDialg = new AlertDialog.Builder(Lst_Food.this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View view = inflater.inflate(R.layout.order_update_layout, null);
            mat = (MaterialSpinner) view.findViewById(R.id.statusBar);
            mat.setItems("Order Placed", "Order is on the way", "Order is shipped");
            alertDialg.setView(view);
            final String lkey = key;
            alertDialg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    item.setStatus(String.valueOf(mat.getSelectedIndex()));
                    db_ref.child(lkey).setValue(item);
                }
            });
            alertDialg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        alertDialg.show();
    }

    public static String statusCode(String code){
        if(code.equals("0")){
            return "your order is placed";
        }else if(code.equals("1")){
            return "your order is on the way";
        }else{
            return "your order is already shipped";
        }
    }


}
