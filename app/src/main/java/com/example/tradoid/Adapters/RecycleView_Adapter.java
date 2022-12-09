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

import com.example.tradoid.R;
import com.example.tradoid.Stock_Page;
import com.example.tradoid.fragments.Stock;

// The Adapter defines how the list look and its items
public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.MyViewHolder> {

    String data1[],data2[];
    int images[];
    Context context;

    //Constructor for the adapter
    public RecycleView_Adapter(Context ct, String[] s1, String[] s2, int[] img) {
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.recycle_view_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // communicates with MyViewHolder
        holder.tv1.setText(data1[position]);
        holder.tv2.setText(data2[position]);
        holder.myImage.setImageResource(images[position]);

        // needed for onClick
        holder.rowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Stock_Page.class);
                // give it extra data
                intent.putExtra("data1",data1[position]);
                intent.putExtra("data2",data2[position]);
                intent.putExtra("myImage",images[position]);
                // start the Stock Page activity
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    //Makes the row with layout we want
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv1,tv2;
        ImageView myImage;
        ConstraintLayout rowlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.recyclerView_row_stock_title);
            tv2 = itemView.findViewById(R.id.recyclerView_row_stock_subTitle);
            myImage = itemView.findViewById(R.id.recyclerView_row_stock_icon);
            // needed for onClick
            rowlayout = itemView.findViewById(R.id.recycle_row_layout);
        }
    }
}
