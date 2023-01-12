package com.example.tradoid.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.text.DecimalFormat;
import java.util.List;

public class status_RecycleView_Adapter extends RecyclerView.Adapter<status_RecycleView_Adapter.MyViewHolder>{

    Context context;
    List<Stock> item_list;
    List<double[]> amount_list;
    int[] colors;
    boolean admin;
    User user;

    //Constructor for the adapter
    public status_RecycleView_Adapter(Context ct, List<Stock> newList, List<double[]> amount_list, int[] colors, boolean admin, String params) {
        context = ct;
        item_list = newList;
        this.amount_list = amount_list;
        this.admin = admin;
        this.colors = colors;
        if (!admin){
            this.user = new Gson().fromJson(params, User.class);
        }
    }

    @NonNull
    @Override
    public status_RecycleView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.status_row, parent,false);
        return new status_RecycleView_Adapter.MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull status_RecycleView_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Limiting the Decimal length
        DecimalFormat numberFormat = new DecimalFormat("#.0");

        // communicates with MyViewHolder
        holder.tv1.setText(item_list.get(position).getStockId());
        holder.tv2.setText(item_list.get(position).getFullName());
        holder.tv3.setText("$" + numberFormat.format(amount_list.get(position)[0]));
        holder.tv4.setText("Stocks: " + numberFormat.format(amount_list.get(position)[1]));
        //add colors
        holder.dot.setColorFilter(colors[position]);

        // needed for onClick
        if (!admin) {
            holder.rowLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, Stock_Page.class);
                // give it extra data
                intent.putExtra("name", item_list.get(position).getStockId());
                intent.putExtra("full_name", item_list.get(position).getFullName());
                intent.putExtra("price", item_list.get(position).getCurrentPrice());
                intent.putExtra("price_change", item_list.get(position).getChange());
                // give it the screen it came from
                intent.putExtra("former Screen", "Status_Page");
                intent.putExtra("extra", new Gson().toJson(this.user));
                // start the Stock Page activity
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
        //return item_list.size();
    }

    //Makes the row with layout we want
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1,tv2,tv3,tv4;
        ImageView dot;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.status_row_tv_name);
            tv2 = itemView.findViewById(R.id.status_row_tv_full_name);
            tv3 = itemView.findViewById(R.id.status_row_tv_usd);
            tv4 = itemView.findViewById(R.id.status_row_tv_stocks);
            dot = itemView.findViewById(R.id.status_row_color_dot);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.status_row_layout);
        }
    }
}
