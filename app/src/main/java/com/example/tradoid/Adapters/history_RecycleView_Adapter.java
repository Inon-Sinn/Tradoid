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
import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class history_RecycleView_Adapter extends RecyclerView.Adapter<history_RecycleView_Adapter.MyViewHolder>{

    Context context;

    User user;

    Gson gson = new Gson();

    List<String[]> historyList = new ArrayList<>();

    public HttpUtils client = new HttpUtils();

    public history_RecycleView_Adapter(Context ct, List<String[]> historyList, Map<String, String> params) {
        this.context = ct;
        this.historyList = historyList;
        this.user = gson.fromJson(params.get("user"), User.class);
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
        holder.tv1.setText(historyList.get(position)[0]);
        holder.tv2.setText(historyList.get(position)[1]);
        holder.tv3.setText(historyList.get(position)[2]);
    }


    @Override
    public int getItemCount() {
        return historyList.size();
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
