package com.example.jarvis.sendmefoodclient.MenuHld;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jarvis.sendmefoodclient.Current.Current;
import com.example.jarvis.sendmefoodclient.Lst_Food;
import com.example.jarvis.sendmefoodclient.Model.RqData;
import com.example.jarvis.sendmefoodclient.R;

import java.util.List;

class MyViewHld extends RecyclerView.ViewHolder{

    public TextView name,price,addrs,numbers,id;
    public Button detail;

    public MyViewHld(View itemView) {
        super(itemView);

        id = (TextView) itemView.findViewById(R.id.order_idkey);
        name = (TextView) itemView.findViewById(R.id.order_status_item_name);
        price = (TextView) itemView.findViewById(R.id.order_item_stat);
        addrs = (TextView) itemView.findViewById(R.id.order_stat_address);
        numbers = (TextView) itemView.findViewById(R.id.order_status_phone);
        detail = (Button) itemView.findViewById(R.id.button_dtl_food);
    }
}

public class RqDataAdapter extends RecyclerView.Adapter<MyViewHld>{

    List<RqData> LstRq;
    Context context;

    public RqDataAdapter(List<RqData> lstRq,Context context) {
        this.LstRq = lstRq;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHld onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_status_layout,parent,false);
        return new MyViewHld(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHld holder, final int position) {
        holder.id.setText("#"+LstRq.get(position).getKey());
        holder.name.setText(LstRq.get(position).getName());
        holder.price.setText(LstRq.get(position).getSum());
        holder.addrs.setText(LstRq.get(position).getAddress());
        holder.numbers.setText(LstRq.get(position).getNumbers());

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Lst_Food.class);
                Current.currentRqData = LstRq.get(position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return LstRq.size();
    }
}
