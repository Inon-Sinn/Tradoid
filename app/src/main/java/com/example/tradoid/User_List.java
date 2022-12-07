package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class User_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Creating a custom Toolbar
        Toolbar user_list_toolbar = findViewById(R.id.toolbar_user_list);
        setSupportActionBar(user_list_toolbar);

        // Connection to User Status TODO only temporary remove later
        Button to_User_Status_btn = findViewById(R.id.user_list_To_user_status);
        to_User_Status_btn.setOnClickListener(v -> sendToActivity(User_Status.class));
    }

    // Creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return true;
    }

    // Makes the menu items clickable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_user_list) {
            //sendToActivity(User_List.class); Already on this page
            return true;
        }
        else if (item.getItemId() == R.id.menu_dashboard) {
            sendToActivity(Dashboard.class);
            return true;
        }
        else if (item.getItemId() == R.id.menu_admin_logout) {
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