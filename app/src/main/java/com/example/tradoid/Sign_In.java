package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Sign_In extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Creating a custom Toolbar
        Toolbar sign_in_toolbar = findViewById(R.id.toolbar_sign_in);
        setSupportActionBar(sign_in_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.sign_in_To_login);
        back_arrow.setOnClickListener(v -> sendToActivity(login.class));

        // Connection to Stock Market TODO only temporary remove later
        Button to_Stock_Market_btn = findViewById(R.id.sign_in_To_stock_market);
        to_Stock_Market_btn.setOnClickListener(v -> sendToActivity(Stock_Market.class));

        // Connection to User List TODO only temporary remove later
        Button to_User_List_btn = findViewById(R.id.sign_in_To_user_list);
        to_User_List_btn.setOnClickListener(v -> sendToActivity(User_List.class));
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}