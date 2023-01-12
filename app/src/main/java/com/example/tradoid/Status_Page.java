package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tradoid.Adapters.status_RecycleView_Adapter;
import com.example.tradoid.Data_handling.stock_data;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.Data_handling.user_data;
import com.example.tradoid.backend.*;
import com.example.tradoid.firebase.model.BalanceViewModel;
import com.example.tradoid.backend.Stock;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class Status_Page extends AppCompatActivity {

    User user;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_page);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        // display user balance
        TextView tv_balance = findViewById(R.id.status_page_tv_amount);

        tv_balance.setText("$" + user.getBalance());

        // get user_data
        user_data user_temp = new user_data("Temp","Temp",0, "Temp");

        // Connect to View Model and getting data
        stock_view_model view_model = new ViewModelProvider(this).get(stock_view_model.class);
        List<Stock> data = view_model.getDataList();
        List<double[]> stock_count = user_temp.getStock_amount();

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_status_pg);

        // Perform item selected listener

        Map<String, String> params = new HashMap<>();
        params.put("user", gson.toJson(user));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_menu_stock_market) {
                sendToActivity(Stock_Market.class, params);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_menu_profile) {
                sendToActivity(Profile.class, params);
                return true;
            }
            return true;
        });

        // Checking Light Mode
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Creating the Donut chart
        DonutProgressView donutView = findViewById(R.id.donut_char_status_page);
        donutView.setCap(1f);
        float section_num = 0; //data.size()
        List<DonutSection> sections = new ArrayList<>();
        int[] colors = new int[0]; //data.size()
        String section_name;
        for (int i = 0; i < section_num; i++) {
            float hue = 0;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    hue = (120/(section_num))*i + 180;//we're using the light theme
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    hue = (60/(section_num))*i + 0;// we're using dark theme
                    break;
            }
            colors[i] = Color.HSVToColor(new float[]{hue,(float)0.9,(float) 1});
            section_name = "Section " + i;
            sections.add(new DonutSection(section_name, colors[i], (float) stock_count.get(i)[0]));
        }
        donutView.submitData(sections);

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_status_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        status_RecycleView_Adapter adapter = new status_RecycleView_Adapter(this, data,stock_count,colors,false, gson.toJson(user));
        recyclerView.setAdapter(adapter);

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