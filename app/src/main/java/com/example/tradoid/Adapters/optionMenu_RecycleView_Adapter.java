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
import com.example.tradoid.backend.User;
import com.example.tradoid.login;
import com.example.tradoid.section_balance;
import com.example.tradoid.section_history;
import com.example.tradoid.section_notification;
import com.google.gson.Gson;

import java.util.Map;


public class optionMenu_RecycleView_Adapter extends RecyclerView.Adapter<optionMenu_RecycleView_Adapter.MyViewHolder>{

    String[] section_names = {"Notification","History","Balance","Log Out"};
    int[] section_icons = {R.drawable.ic_notification,R.drawable.ic_history,R.drawable.ic_balance,R.drawable.ic_logout};
    Class[] section_classes = new Class[]{section_notification.class,section_history.class, section_balance.class, login.class};
    Context context;
    Map<String, String> params;

    Gson gson = new Gson();

    public optionMenu_RecycleView_Adapter(Context ct, Map<String, String> params) {
        this.context = ct;
        this.params = params;
    }

    public optionMenu_RecycleView_Adapter(String[] section_names, int[] section_icons, Class[] section_classes, Context context, Map<String, String> params) {
        this.section_names = section_names;
        this.section_icons = section_icons;
        this.section_classes = section_classes;
        this.context = context;
        this.params = params;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(context);
        //We give it the row layout we want
        View view = layoutInflater.inflate(R.layout.profile_row, parent,false);
        return new optionMenu_RecycleView_Adapter.MyViewHolder(view);
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
            for (Map.Entry<String, String> param : params.entrySet()){
                intent.putExtra(param.getKey(), param.getValue());
            }
            intent.putExtra("login","logout");
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
