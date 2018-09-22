package com.example.jarvis.sendmefoodclient.MenuHld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jarvis.sendmefoodclient.Model.RqData;
import com.example.jarvis.sendmefoodclient.R;

import java.util.List;

class MyViewHld extends RecyclerView.ViewHolder{

    public TextView name,price,addrs;

    public MyViewHld(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.order_status_item_name);
        price = (TextView) itemView.findViewById(R.id.order_item_stat);
        addrs = (TextView) itemView.findViewById(R.id.order_stat_address);
    }
}

public class RqDataAdapter extends RecyclerView.Adapter<MyViewHld>   {

    List<RqData> LstRq;

    public RqDataAdapter(List<RqData> lstRq) {
        this.LstRq = lstRq;
    }

    @NonNull
    @Override
    public MyViewHld onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_status_layout,parent,false);
        return new MyViewHld(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHld holder, int position) {
        holder.name.setText(LstRq.get(position).getName());
        holder.price.setText(LstRq.get(position).getSum());
        holder.addrs.setText(LstRq.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return LstRq.size();
    }
}
