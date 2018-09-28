package com.example.jarvis.sendmefoodclient.MenuHld;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.R;

public class MyOrderHld  extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener  {
    public TextView myFoodCat;
    public TextView myFoodName;
    public TextView myFoodDiscount;
    public TextView myFoodPrice;
    public ImageView myImg;
    ListenerClck listenerClck;

    public MyOrderHld(View itemView) {
        super(itemView);
        myFoodCat = itemView.findViewById(R.id.my_food_cat);
        myFoodName = itemView.findViewById(R.id.my_food_name);
        myFoodDiscount = itemView.findViewById(R.id.my_food_discount);
        myFoodPrice = itemView.findViewById(R.id.my_food_price);
        myImg = itemView.findViewById(R.id.fd_img);

        itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);
    }

    public void setlistenerClck(ListenerClck listenerClck){
        this.listenerClck = listenerClck;
    }

    @Override
    public void onClick(View v) {
        listenerClck.onClick(getAdapterPosition(),v,false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,1,getAdapterPosition(), Current.DELETE);
    }
}
