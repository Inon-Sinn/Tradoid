package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_profile);

        // Perfom item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bottom_menu_stock_market:
                        sendToActivity(Stock_Market.class);
                        return true;
                    case R.id.bottom_menu_status_pg:
                        sendToActivity(Status_Page.class);
                        return true;
                    case R.id.bottom_menu_profile:
                        return true;
                }
                return false;
            }
        });
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}