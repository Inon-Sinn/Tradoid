package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.Owned;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.example.tradoid.backend.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class Dashboard extends AppCompatActivity {

    //Card View with round edges
    //https://www.youtube.com/watch?v=kGs7e4Wb67I&t=23s&ab_channel=IJApps

    String adminId;

    public HttpUtils client = new HttpUtils();

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (getIntent().hasExtra("adminId")) {
            adminId = getIntent().getStringExtra("adminId");
        }

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewAdmin);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_dashboard);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Map<String, String> params = new HashMap<>();
            params.put("adminId", adminId);

            if (item.getItemId() == R.id.bottom_menu_user_list) {
                sendToActivity(User_List.class, params);
                return true;
            }
            if (item.getItemId() == R.id.bottom_menu_options) {
                sendToActivity(admin_options.class, params);
                return true;
            }
            return true;
        });

        // Checking Light Mode
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Getting the amount of user,admins and banned users
        List<Integer> user_donut_values = new ArrayList<>();

        Response response = client.sendGet("get_amounts");
        Amounts amounts = new Amounts(0, 0, 0, 0, 0);
        if (response.passed()) {
            amounts = gson.fromJson(response.getData(), Amounts.class);
        }

        user_donut_values.add(amounts.getAdminAmount()); // admins
        user_donut_values.add(amounts.getUnbannedAmount()); // user
        user_donut_values.add(amounts.getBannedAmount()); // banned

        // Total Amount of Revenue
        TextView total_revenue = findViewById(R.id.tv_total_revenue);
        String total_revenue_txt = "$" + amounts.getTotalRevenue();
        total_revenue.setText(total_revenue_txt);

        // TextView of the user,admin,banned options
        TextView admin_amount = findViewById(R.id.dashbaord_admin_amount);
        TextView user_amount = findViewById(R.id.dashbaord_user_amount);
        TextView banned_amount = findViewById(R.id.dashbaord_banned_amount);

        // Set The amount of user
        String admin_txt = "Admins: " + user_donut_values.get(0);
        admin_amount.setText(admin_txt);
        String user_txt = "User: " + user_donut_values.get(1);
        user_amount.setText(user_txt);
        String Banned_txt = "Banned: " + user_donut_values.get(2);
        banned_amount.setText(Banned_txt);

        // Total Amount of user
        TextView total_user = findViewById(R.id.tv_total_users);
        String total_user_txt = String.valueOf(user_donut_values.get(0) + user_donut_values.get(1) + user_donut_values.get(2));
        total_user.setText(total_user_txt);

        // Creating the user Donut chart
        DonutProgressView donutView = findViewById(R.id.dashboard_user_donut);
        donutView.setCap(1f);
        List<DonutSection> sections = new ArrayList<>();
        int[] colors = new int[user_donut_values.size()];
        String section_name;
        for (int i = 0; i < user_donut_values.size(); i++) {
            float hue = 0;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    hue = (120 / (user_donut_values.size())) * i + 180;//we're using the light theme
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    hue = (60 / (user_donut_values.size())) * i + 0;// we're using dark theme
                    break;
            }
            colors[i] = Color.HSVToColor(new float[]{hue, (float) 0.9, (float) 1});
            section_name = "Section " + i;
            sections.add(new DonutSection(section_name, colors[i], (float) user_donut_values.get(i)));
        }
        donutView.submitData(sections);


        // Add the section colors
        ImageView admin_color = findViewById(R.id.dashboard_admin_color_dot);
        admin_color.setColorFilter(colors[0]);
        ImageView user_color = findViewById(R.id.dashboard_user_color_dot);
        user_color.setColorFilter(colors[1]);
        ImageView banned_color = findViewById(R.id.dashboard_banned_color_dot);
        banned_color.setColorFilter(colors[2]);
    }


    // Sends to other screens
    public void sendToActivity(Class cls, Map<String, String> params){
        Intent intent = new Intent(this, cls);
        for (Map.Entry<String, String> param: params.entrySet()){
            intent.putExtra(param.getKey(), param.getValue());
        }
        startActivity(intent);
    }
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}