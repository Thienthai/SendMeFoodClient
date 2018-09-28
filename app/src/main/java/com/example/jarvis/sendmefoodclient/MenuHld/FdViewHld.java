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

public class FdViewHld extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView fd_name;
    public ImageView fd_image;

    public ListenerClck listenerClck;

    public FdViewHld(View itemView) {
        super(itemView);

        fd_name = (TextView)itemView.findViewById(R.id.fd_name);
        fd_image = (ImageView)itemView.findViewById(R.id.fd_image);

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
