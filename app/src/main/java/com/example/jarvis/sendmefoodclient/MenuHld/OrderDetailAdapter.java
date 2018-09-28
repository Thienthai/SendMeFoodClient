package com.example.jarvis.sendmefoodclient.MenuHld;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.jarvis.sendmefoodclient.Model.Orders;
import com.example.jarvis.sendmefoodclient.R;

import android.app.Activity;

import java.util.List;

class OrderDtlHld extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{


    public TextView name,price,status;

    public ImageView qntity;

    public OrderDtlHld(View itemView) {
        super(itemView);

        name = (TextView) itemView.findViewById(R.id.order_detail_item_name);
        price = (TextView) itemView.findViewById(R.id.order_detail_item_price);
        status = (TextView) itemView.findViewById(R.id.order_detail_item_status);
        qntity = (ImageView) itemView.findViewById(R.id.order_detail_item_qntity);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0,0,getAdapterPosition(),"Update");
    }
}

public class  OrderDetailAdapter extends RecyclerView.Adapter<OrderDtlHld> {

    List<Orders> myOrders;

    public OrderDetailAdapter(List<Orders> myOrders) {
        this.myOrders = myOrders;
    }



    @NonNull
    @Override
    public OrderDtlHld onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_list,parent,false);
        return new OrderDtlHld(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull OrderDtlHld holder, int position) {
        holder.name.setText(myOrders.get(position).getProdName());
        holder.price.setText(String.format("Price: %s",myOrders.get(position).getPrice()));
        holder.status.setText(String.format("Status: %s",stat(myOrders.get(position).getStatus())));
//        TextDrawable drawable = TextDrawable.builder().buildRound(""+myOrders.get(position).getQntity(), Color.RED);
//        holder.qntity.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }

    private String stat(String stat) {
        if("0".equals(stat)){
            return "Order is Already Place";
        }else if(stat.equals("1")){
            return "Food is on the way";
        }else{
            return "Food is already arrive";
        }
    }


}
