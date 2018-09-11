package com.example.jarvis.sendmefoodclient;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.MenuHld.OrderStatusHld;
import com.example.jarvis.sendmefoodclient.Model.RqData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderBoard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<RqData,OrderStatusHld> adapter;
    FirebaseDatabase db;
    DatabaseReference db_ref;
    MaterialSpinner mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_board);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.list_orders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getOrders();
    }

    private void getOrders() {
        adapter = new FirebaseRecyclerAdapter<RqData, OrderStatusHld>(
                RqData.class,
                R.layout.order_status_layout,
                OrderStatusHld.class,
                db_ref
        ) {
            @Override
            protected void populateViewHolder(OrderStatusHld viewHolder, RqData model, int position) {
                viewHolder.order_id.setText(adapter.getRef(position).getKey());
                viewHolder.order_status.setText(Current.statusCode(model.getStatus()));
                viewHolder.order_phone.setText(model.getNumbers());
                viewHolder.setListenerClck(new ListenerClck() {
                    @Override
                    public void onClick(int Pos, View view, boolean isClck) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().equals("Update")){
            updtDialg(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if(item.getTitle().equals("Delete")){
            deleteDialg(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    private void deleteDialg(String key) {
        db_ref.child(key).removeValue();
    }

    private void updtDialg(String key, final RqData item) {
        final AlertDialog.Builder alertDialg = new AlertDialog.Builder(OrderBoard.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.order_update_layout,null);
        mat = (MaterialSpinner)view.findViewById(R.id.statusBar);
        mat.setItems("Order Placed","Order is on the way","Order is shipped");
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

}
