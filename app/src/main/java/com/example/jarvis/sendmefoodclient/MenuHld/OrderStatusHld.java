package com.example.jarvis.sendmefoodclient.MenuHld;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.R;

public class OrderStatusHld extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView order_id,order_name,order_status,order_phone,order_address;

    ListenerClck listenerClck;

    public OrderStatusHld(View itemView) {
        super(itemView);

        order_id = itemView.findViewById(R.id.order_status_item_name);
        order_name = itemView.findViewById(R.id.order_food_item_name);
        order_status = itemView.findViewById(R.id.order_item_stat);
        order_phone = itemView.findViewById(R.id.order_status_phone);
        order_address = itemView.findViewById(R.id.order_stat_address);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setListenerClck(ListenerClck listenerClck) {
        this.listenerClck = listenerClck;
    }

    @Override
    public void onClick(View v) {
        listenerClck.onClick(getAdapterPosition(),v,false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,getAdapterPosition(),"Update");
        menu.add(0,1,getAdapterPosition(),"Delete");
    }
}
