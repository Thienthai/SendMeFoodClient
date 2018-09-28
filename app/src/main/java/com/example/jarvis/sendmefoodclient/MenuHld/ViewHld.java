package com.example.jarvis.sendmefoodclient.MenuHld;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Interface.ListenerClck;
import com.example.jarvis.sendmefoodclient.R;

public class ViewHld extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView name_menu;
    public ImageView img;

    public ListenerClck listenerClck;

    public ViewHld(View itemView) {
        super(itemView);

        img = (ImageView) itemView.findViewById(R.id.menu_image);
        name_menu = (TextView) itemView.findViewById(R.id.name_menu);

//        itemView.setOnCreateContextMenuListener(this);
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
        menu.add(0,0,getAdapterPosition(), Current.UPDATE);
        menu.add(0,1,getAdapterPosition(), Current.DELETE);
    }
}
