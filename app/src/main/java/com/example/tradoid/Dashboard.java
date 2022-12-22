package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Dashboard extends AppCompatActivity {

    //Card View with round edges
    //https://www.youtube.com/watch?v=kGs7e4Wb67I&t=23s&ab_channel=IJApps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Creating a custom Toolbar
        Toolbar dashboard_toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(dashboard_toolbar);
    }

    // Creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_general_menu,menu);
        return true;
    }

    // Makes the menu items clickable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_general_user_list) {
            sendToActivity(User_List.class);
            return true;
        }
        else if (item.getItemId() == R.id.menu_general_dashboard) {
            //sendToActivity(Dashboard.class); Already on this page
            return true;
        }
        else if (item.getItemId() == R.id.menu_general_admin_logout) {
            sendToActivity(login.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}