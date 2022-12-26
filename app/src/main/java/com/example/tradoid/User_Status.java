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
import java.util.ArrayList;
import java.util.List;
import app.futured.donut.DonutProgressView;
import app.futured.donut.DonutSection;

public class User_Status extends AppCompatActivity {

    // Views with Dynamic values
    TextView tv_name;

    // The Dynamic values
    String name;
    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // Creating a custom Toolbar
        Toolbar user_status_toolbar = findViewById(R.id.toolbar_user_status);
        setSupportActionBar(user_status_toolbar);

        // Implementing connecting to user ban
        ImageView to_ban = findViewById(R.id.ban_user_status);
        to_ban.setOnClickListener(v->sendToActivity(user_ban.class));

        // Implementing the Back arrow in the Toolbar
        TextView back_arrow = findViewById(R.id.user_status_back_arrow);
        back_arrow.setOnClickListener(v -> sendToActivity(User_List.class));

        // Connecting to the TextViews With Dynamic Values
        tv_name = findViewById(R.id.user_status_name);

        // Get the data form the row it was clicked on
        getData();
        setData();

        // get user_data
        user_data user = new user_data("Temp","Temp",0);

        // Connect to Stock View Model and getting data
        stock_view_model view_model = new ViewModelProvider(this).get(stock_view_model.class);
        view_model.setUser(user,"status page");
        List<stock_data> data = view_model.getData_list();
        List<double[]> stock_count = user.getStock_amount();

        // Checking Light Mode
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        // Creating the Donut chart
        DonutProgressView donutView = findViewById(R.id.donut_char_user_status);
        donutView.setCap(1f);
        float section_num = (float) data.size();
        List<DonutSection> sections = new ArrayList<>();
        int[] colors = new int[data.size()];
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

    // getting the data from the intent
    private void getData(){
        // Check if it received the data
        if (getIntent().hasExtra("name")){
            name = getIntent().getStringExtra("name");
        }else{
            // In Case something went wrong
            Toast.makeText(this,"Error: No data",Toast.LENGTH_SHORT).show();
        }
    }

    // setting the data to our elements(image and text views)
    private void setData(){
        tv_name.setText(name);
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("name",name);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}