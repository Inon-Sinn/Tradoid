package com.example.tradoid.Adapters;

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
import com.example.tradoid.login;
import com.example.tradoid.section_balance;
import com.example.tradoid.section_history;
import com.example.tradoid.section_notification;


public class profile_RecycleView_Adapter extends RecyclerView.Adapter<profile_RecycleView_Adapter.MyViewHolder>{

    String[] section_names = {"Notification","History","Balance","Log Out"};
    int[] section_icons = {R.drawable.ic_notification,R.drawable.ic_history,R.drawable.ic_balance,R.drawable.ic_logout};
    Class[] section_classes = new Class[]{section_notification.class,section_history.class, section_balance.class, login.class};
    Context context;
    String user_ID;

    public profile_RecycleView_Adapter(Context ct,String user_ID) {
        this.context = ct;
        this.user_ID = user_ID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.profile_row, parent,false);
        return new profile_RecycleView_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // communicates with MyViewHolder
        holder.tv1.setText(section_names[position]);
        holder.icon.setImageResource(section_icons[position]);

        // needed for onClick
        holder.rowLayout.setOnClickListener(v -> {
            // start the chosen activity
            Intent intent = new Intent(context, section_classes[position]);
            intent.putExtra("user_ID",user_ID);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return section_names.length;
    }

    //Makes the row with layout we want
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        ImageView icon;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.profile_row_section_name);
            icon = itemView.findViewById(R.id.profile_row_section_icon);
            // needed for onClick
            rowLayout = itemView.findViewById(R.id.profile_row_layout);
        }
    }

}
