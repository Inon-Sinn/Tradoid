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
import com.example.tradoid.backend.Owned;
import com.example.tradoid.backend.Stock;
import com.example.tradoid.backend.User;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class status_RecycleView_Adapter extends RecyclerView.Adapter<status_RecycleView_Adapter.MyViewHolder>{

    Context context;
    int[] colors;
    boolean admin;
    List<Owned> ownedList;

    Map<String, String> params;

    Gson gson = new Gson();

    //Constructor for the adapter
    public status_RecycleView_Adapter(Context ct, List<Owned> ownedList, int[] colors, boolean admin, Map<String, String> params) {
        context = ct;
        this.ownedList = ownedList;
        this.admin = admin;
        this.colors = colors;
        this.params = params;
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
        holder.tv1.setText(ownedList.get(position).getStock().getStockId());
        holder.tv2.setText(ownedList.get(position).getStock().getFullName());
        holder.tv3.setText("$" + numberFormat.format(
                ownedList.get(position).getAmount() * ownedList.get(position).getStock().getCurrentPrice()));
        holder.tv4.setText("Stocks: " + numberFormat.format(ownedList.get(position).getAmount()));
        //add colors
        holder.dot.setColorFilter(colors[position]);

        // needed for onClick
        if (!admin) {
            holder.rowLayout.setOnClickListener(v -> {
                Intent intent = new Intent(context, Stock_Page.class);

                intent.putExtra("stock", gson.toJson(ownedList.get(position).getStock()));
                if (params.containsKey("user")){
                    intent.putExtra("user", params.get("user"));
                }
                if (params.containsKey("adminId")){
                    intent.putExtra("adminId", params.get("adminId"));
                }
                intent.putExtra("formerScreen", "Status_Page");
                // start the Stock Page activity
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return ownedList.size();
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
