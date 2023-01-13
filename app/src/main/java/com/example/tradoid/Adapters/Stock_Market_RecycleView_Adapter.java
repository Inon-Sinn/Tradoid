package com.example.tradoid.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tradoid.Data_handling.stock_data;
import com.example.tradoid.R;
import com.example.tradoid.Stock_Page;
import com.example.tradoid.backend.Stock;
import com.example.tradoid.backend.User;
import com.google.gson.Gson;

import java.util.List;

// The Adapter defines how the list look and its items
public class Stock_Market_RecycleView_Adapter extends RecyclerView.Adapter<Stock_Market_RecycleView_Adapter.MyViewHolder> {

    Context context;
    List<Stock> item_list;
    User user;

    Gson gson = new Gson();

    //Constructor for the adapter
    public Stock_Market_RecycleView_Adapter(Context ct, List<Stock> newList, User user) {
        this.context = ct;
        this.item_list = newList;
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.stock_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String text;
        // communicates with MyViewHolder
        holder.tv1.setText(item_list.get(position).getStockId());
        holder.tv2.setText(item_list.get(position).getFullName());
        holder.tv3.setText(String.valueOf(item_list.get(position).getCurrentPrice()));
        if(item_list.get(position).getChange()<0){
            holder.tv4.setText(String.valueOf(item_list.get(position).getChange()));
            holder.tv4.setTextColor(Color.RED);
        }
        else {
            text = "+" + item_list.get(position).getChange();
            holder.tv4.setText(text);
            holder.tv4.setTextColor(Color.GREEN);
        }


        // needed for onClick
        holder.rowLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Stock_Page.class);
            // give it extra data
            intent.putExtra("stock", gson.toJson(item_list.get(position)));
            // give it the screen it came from
            intent.putExtra("formerScreen","Stock_Market");
            intent.putExtra("user", gson.toJson(user));
            // start the Stock Page activity
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if (item_list.isEmpty()){
            return 0;
        }
        return item_list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Stock> update){
        item_list.clear();
        item_list.addAll(update);
        notifyDataSetChanged();
    }

    //Makes the row with layout we want
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1,tv2,tv3,tv4;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.stock_row_tv_name);
            tv2 = itemView.findViewById(R.id.stock_row_tv_full_name);
            tv3 = itemView.findViewById(R.id.stock_row_tv_stockPrice);
            tv4 = itemView.findViewById(R.id.stock_row_tv_price_change);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.stock_row_layout);
        }
    }
}
