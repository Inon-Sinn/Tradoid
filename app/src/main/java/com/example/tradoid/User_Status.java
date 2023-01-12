package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tradoid.Adapters.status_RecycleView_Adapter;
import com.example.tradoid.Data_handling.stock_data;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.Data_handling.user_data;
import com.example.tradoid.backend.Stock;
import com.example.tradoid.backend.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class User_Status extends AppCompatActivity {

    // Views with Dynamic values
    TextView tv_name;
    TextView tv_balance;

    User user;
    String adminId;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        if (getIntent().hasExtra("adminId")) {
            adminId = getIntent().getStringExtra("adminId");
        }

        // Creating a custom Toolbar
        Toolbar user_status_toolbar = findViewById(R.id.toolbar_user_status);
        setSupportActionBar(user_status_toolbar);

        // Implementing connecting to user ban
        ImageView to_ban = findViewById(R.id.ban_user_status);
        Map<String, String> params = new HashMap<>();
        params.put("user", gson.toJson(user));
        params.put("adminId", adminId);
        to_ban.setOnClickListener(v->sendToActivity(user_ban.class, params));

        // Implementing the Back arrow in the Toolbar
        TextView back_arrow = findViewById(R.id.user_status_back_arrow);
        back_arrow.setOnClickListener(v -> sendToActivity(User_List.class));

        // Connecting to the TextViews With Dynamic Values
        tv_name = findViewById(R.id.user_status_name);
        tv_balance = findViewById(R.id.user_page_tv_amount);

        // Get the data form the row it was clicked on
        tv_name.setText(user.getUsername());
        tv_balance.setText("$" + user.getBalance());

        // get user_data
        user_data user = new user_data("Temp","Temp",0, "Temp");

        // Connect to Stock View Model and getting data
        stock_view_model view_model = new ViewModelProvider(this).get(stock_view_model.class);
        List<Stock> data = view_model.getDataList();
        List<double[]> stock_count = user.getStock_amount();

        // Checking Light Mode
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Creating the Donut chart
        DonutProgressView donutView = findViewById(R.id.donut_char_user_status);
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
        RecyclerView recyclerView = findViewById(R.id.recyclerView_user_status);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        status_RecycleView_Adapter adapter = new status_RecycleView_Adapter(this, data,stock_count,colors,true,"");
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