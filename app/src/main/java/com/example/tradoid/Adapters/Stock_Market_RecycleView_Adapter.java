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
import com.example.tradoid.Data_handling.example_Item;

import java.util.ArrayList;
import java.util.List;

// The Adapter defines how the list look and its items
public class Stock_Market_RecycleView_Adapter extends RecyclerView.Adapter<Stock_Market_RecycleView_Adapter.MyViewHolder> {


    Context context;
    List<stock_data> example_list = new ArrayList<>();

    //Constructor for the adapter
    public Stock_Market_RecycleView_Adapter(Context ct, List<stock_data> newList) {
        context = ct;
        example_list = newList;
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
        // communicates with MyViewHolder
        holder.tv1.setText(example_list.get(position).getName());
        holder.tv2.setText(example_list.get(position).getFull_name());
        holder.tv3.setText(String.valueOf(example_list.get(position).getTotal_Price()));
        holder.tv4.setText(String.valueOf(example_list.get(position).getPrice_change()));
        holder.myImage.setImageResource(example_list.get(position).getIcon());

        // needed for onClick
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Stock_Page.class);
                // give it extra data
                intent.putExtra("data1",example_list.get(position).getName());
                intent.putExtra("data2",example_list.get(position).getFull_name());
                intent.putExtra("data3",example_list.get(position).getTotal_Price());
                intent.putExtra("data4",example_list.get(position).getPrice_change());
                intent.putExtra("myImage",example_list.get(position).getIcon());
                // start the Stock Page activity
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return example_list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<stock_data> update){
        List<stock_data> copy = new ArrayList<>(update);
        example_list.clear();
        example_list.addAll(copy);
        notifyDataSetChanged();
    }

    //Makes the row with layout we want
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1,tv2,tv3,tv4;
        ImageView myImage;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.stock_row_tv_name);
            tv2 = itemView.findViewById(R.id.stock_row_tv_full_name);
            tv3 = itemView.findViewById(R.id.stock_row_tv_stockPrice);
            tv4 = itemView.findViewById(R.id.stock_row_tv_price_change);
            myImage = itemView.findViewById(R.id.stock_row_icon_price_change);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.stock_row_layout);
        }
    }
}
