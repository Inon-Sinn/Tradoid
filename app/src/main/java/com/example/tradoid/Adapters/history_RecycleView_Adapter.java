package com.example.tradoid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tradoid.R;

public class history_RecycleView_Adapter extends RecyclerView.Adapter<history_RecycleView_Adapter.MyViewHolder>{

    String[][] past_events = {{"TSLA","21/12/22","Bought $15"},{"AAPL","25/12/2022","Sold $2200"}};
    Context context;

    public history_RecycleView_Adapter(Context ct) {
        this.context = ct;
    }

    @NonNull
    @Override
    public history_RecycleView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.history_row, parent,false);
        return new history_RecycleView_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull history_RecycleView_Adapter.MyViewHolder holder, int position) {
        // communicates with MyViewHolder
        holder.tv1.setText(past_events[position][0]);
        holder.tv2.setText(past_events[position][1]);
        holder.tv3.setText(past_events[position][2]);
    }


    @Override
    public int getItemCount() {
        return past_events.length;
    }

    //Makes the row with layout we want
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1,tv2,tv3;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.history_row_name);
            tv2 = itemView.findViewById(R.id.history_row_date);
            tv3 = itemView.findViewById(R.id.history_row_state);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.history_row_layout);
        }
    }
}
