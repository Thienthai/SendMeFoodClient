package com.example.jarvis.sendmefoodclient;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.MenuHld.OrderStatusHld;
import com.example.jarvis.sendmefoodclient.MenuHld.RqDataAdapter;
import com.example.jarvis.sendmefoodclient.Model.ListRq;
import com.example.jarvis.sendmefoodclient.Model.Orders;
import com.example.jarvis.sendmefoodclient.Model.RqData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class OrderBoard extends AppCompatActivity {

    private static final String TAG = "d";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

//    FirebaseRecyclerAdapter<RqData,OrderStatusHld> adapter;
    FirebaseDatabase db;
    DatabaseReference db_ref;
    MaterialSpinner mat;

    RqData myRq;
    List<RqData> myNewRq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_board);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("Requests");

        myNewRq = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.list_orders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



//        db_ref.setValue(myRq);

//        getOrders();
    }

    @Override
    protected void onStart() {
        super.onStart();

        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myNewRq.clear();

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    RqData rqDat = data.getValue(RqData.class);
                    rqDat.setKey(data.getKey());
                    myNewRq.add(rqDat);
                }
                RqDataAdapter adapter = new RqDataAdapter(filterData(myNewRq),OrderBoard.this);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });


    }



    private List<RqData> filterData(List<RqData> myRq) {
        List<RqData> myNewData = new ArrayList<>();
        int key = 0;
        for(RqData rq:myRq){
            for(Orders myOrder:rq.getOrders()){
                if(myOrder.getOwner().equals(Current.usrCurrent.getNumber())){
                    if(!myNewData.contains(rq)) {
//                        myOrder.setKey(key);
                        myNewData.add(rq);
                    }
                }
            }
        }
        return myNewData;
    }
//
//    private void getOrders() {
//        adapter = new FirebaseRecyclerAdapter<RqData, OrderStatusHld>(
//       //                OrderStatusHld.class,
////                db_ref
////        ) {
////            @Override
////            protected void populateViewHolder(OrderStatusHld viewHolder, RqData model, int position) {
////                viewHolder.order_id.setText(adapter.getRef(position).getKey());
////                viewHolder.order_status.setText(Current.statusCode(model.getStatus()));
////                viewHolder.order_phone.setText(model.getNumbers());
////                viewHolder.setListenerClck(new ListenerClck() {
////                    @Override
////                    public void onClick(int Pos, View view, boolean isClck) {
////
////                    }
////                });
////            }
////        };
////        adapter.notifyDataSetChanged();
////        recyclerView.setAdapter(adapter);
////    }         RqData.class,
//                R.layout.order_status_layout,

//


//    @Override
//    public boolean onContextItemSelected(MenuItem item){
////        if(item.getTitle().equals("Update")){
////            updtDialg(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
////        }else if(item.getTitle().equals("Delete")){
////            deleteDialg(adapter.getRef(item.getOrder()).getKey());
////        }
////        return super.onContextItemSelected(item);
//    }
//
//    private void deleteDialg(String key) {
//        db_ref.child(key).removeValue();
//    }
//
//    private void updtDialg(String key, final RqData item) {
//        final AlertDialog.Builder alertDialg = new AlertDialog.Builder(OrderBoard.this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        final View view = inflater.inflate(R.layout.order_update_layout,null);
//        mat = (MaterialSpinner)view.findViewById(R.id.statusBar);
//        mat.setItems("Order Placed","Order is on the way","Order is shipped");
//        alertDialg.setView(view);
//        final String lkey = key;
//        alertDialg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                item.setStatus(String.valueOf(mat.getSelectedIndex()));
//                db_ref.child(lkey).setValue(item);
//            }
//        });
//        alertDialg.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        alertDialg.show();
//    }

}
