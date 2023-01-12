package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    //Card View with round edges
    //https://www.youtube.com/watch?v=kGs7e4Wb67I&t=23s&ab_channel=IJApps

    String adminId;

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