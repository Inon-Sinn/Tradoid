package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tradoid.Adapters.profile_RecycleView_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    String user_ID;

    @SuppressLint({"NonConstantResourceId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}
        Toast.makeText(this,user_ID,Toast.LENGTH_SHORT).show();
        System.out.println(user_ID);

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        profile_RecycleView_Adapter adapter = new profile_RecycleView_Adapter(this,user_ID);
        recyclerView.setAdapter(adapter);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_profile);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_menu_stock_market:
                    sendToActivity(Stock_Market.class);
                    return true;
                case R.id.bottom_menu_status_pg:
                    sendToActivity(Status_Page.class);
                    return true;
                default:
                    return true;
            }
        });


        // Connect to Text Views for Name and Email
        TextView tv_username = findViewById(R.id.profile_tv_username);
        TextView tv_email = findViewById(R.id.profile_tv_email);

    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}