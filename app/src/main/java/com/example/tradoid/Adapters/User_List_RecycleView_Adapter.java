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

import com.example.tradoid.Data_handling.example_Item;
import com.example.tradoid.R;
import com.example.tradoid.Stock_Page;

import java.util.ArrayList;
import java.util.List;


//TODO Currently only a copy of the Stock Market Adapter


// The Adapter defines how the list look and its items
public class User_List_RecycleView_Adapter extends RecyclerView.Adapter<User_List_RecycleView_Adapter.UserList_ViewHolder> {


    Context context;
    List<example_Item> example_list = new ArrayList<>();

    //Constructor for the adapter
    public User_List_RecycleView_Adapter(Context ct, List<example_Item> newList) {
        context = ct;
        example_list = newList;
    }

    @NonNull
    @Override
    public UserList_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.example_item_row, parent,false);
        return new UserList_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserList_ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // communicates with MyViewHolder
        holder.tv1.setText(example_list.get(position).getData1());
        holder.tv2.setText(example_list.get(position).getData2());
        holder.myImage.setImageResource(example_list.get(position).getImage());

        // needed for onClick
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Stock_Page.class); //TODO change to User Status
                // give it extra data
                intent.putExtra("data1",example_list.get(position).getData1());
                intent.putExtra("data2",example_list.get(position).getData2());
                intent.putExtra("myImage",example_list.get(position).getImage());
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
    public void updateList(List<example_Item> update){
        List<example_Item> copy = new ArrayList<>(update);
        example_list.clear();
        example_list.addAll(copy);
        notifyDataSetChanged();
    }

    //Makes the row with layout we want
    public class UserList_ViewHolder extends RecyclerView.ViewHolder{

        TextView tv1,tv2;
        ImageView myImage;
        ConstraintLayout rowLayout;

        public UserList_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.recyclerView_row_stock_title);
            tv2 = itemView.findViewById(R.id.recyclerView_row_stock_subTitle);
            myImage = itemView.findViewById(R.id.recyclerView_row_stock_icon);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.recycle_row_layout);
        }
    }
}