package com.example.jarvis.sendmefoodclient.MenuHld;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.jarvis.sendmefoodclient.R;

public class LstFoodHld extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
    public TextView Name,Price,Status,Qantity;


    public LstFoodHld(View itemView) {
        super(itemView);

        Name = (TextView) itemView.findViewById(R.id.order_detail_item_name);
        Price = (TextView) itemView.findViewById(R.id.order_detail_item_price);
        Status = (TextView) itemView.findViewById(R.id.order_detail_item_status);
        Qantity = (TextView) itemView.findViewById(R.id.order_detail_item_quantity);

        itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,getAdapterPosition(),"Update");
    }
}
