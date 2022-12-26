package com.example.tradoid.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tradoid.Data_handling.user_data;
import com.example.tradoid.R;
import com.example.tradoid.User_Status;
import java.util.List;

// The Adapter defines how the list look and its items
public class User_List_RecycleView_Adapter extends RecyclerView.Adapter<User_List_RecycleView_Adapter.MyViewHolder> {

    Context context;
    List<user_data> item_list;

    //Constructor for the adapter
    public User_List_RecycleView_Adapter(Context ct, List<user_data> newList) {
        context = ct;
        item_list = newList;
    }

    @NonNull
    @Override
    public User_List_RecycleView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.user_row, parent,false);
        return new User_List_RecycleView_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull User_List_RecycleView_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // communicates with MyViewHolder
        holder.tv1.setText(item_list.get(position).getName());
        holder.tv2.setText(item_list.get(position).getEmail());
        holder.tv3.setText(String.valueOf(item_list.get(position).getTotal_worth()));

        // needed for onClick
        holder.rowLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, User_Status.class);
            // give it extra data
            intent.putExtra("name", item_list.get(position).getName());
            intent.putExtra("user_ID", item_list.get(position).getID());
            intent.putExtra("balance", Double.toString(item_list.get(position).getTotal_worth()));
            // Still not implemented
//            intent.putExtra("signed_up", item_list.get(position));
//            intent.putExtra("last_seen", item_list.get(position));

            // start the Stock Page activity
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<user_data> update){
        item_list.clear();
        item_list.addAll(update);
        notifyDataSetChanged();
    }

    //Makes the row with layout we want
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1,tv2,tv3;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.user_row_tv_name);
            tv2 = itemView.findViewById(R.id.user_row_tv_email);
            tv3 = itemView.findViewById(R.id.user_row_tv_total_amount);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.user_row_layout);
        }
    }
}