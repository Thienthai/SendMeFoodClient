package com.example.jarvis.sendmefoodclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.MenuHld.MyOrderHld;
import com.example.jarvis.sendmefoodclient.MenuHld.OrderStatusHld;
import com.example.jarvis.sendmefoodclient.Model.MyFood;
import com.example.jarvis.sendmefoodclient.Model.RqData;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyFoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<MyFood,MyOrderHld> adapter;
    FirebaseDatabase db;
    DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food_list);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("Foods");

        recyclerView = (RecyclerView) findViewById(R.id.my_food_lists);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getmyfood();
    }

    private void getmyfood() {
        adapter = new FirebaseRecyclerAdapter<MyFood,MyOrderHld>(
                MyFood.class,
                R.layout.my_order_layout,
                MyOrderHld.class,
                db_ref.orderByChild("owner").equalTo(Current.usrCurrent.getNumber())
        ){

            @Override
            protected void populateViewHolder(MyOrderHld viewHolder, MyFood model, int position) {

                    viewHolder.myFoodCat.setText(model.getOwner());
                    viewHolder.myFoodName.setText(model.getName());
                    viewHolder.myFoodDiscount.setText(model.getDiscount());
                    viewHolder.myFoodPrice.setText(model.getPrice());
                    Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.myImg);
                    viewHolder.setlistenerClck(new ListenerClck() {
                        @Override
                        public void onClick(int Pos, View view, boolean isClck) {

                        }
                    });
                }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
