package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.tradoid.Adapters.optionMenu_RecycleView_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_options extends AppCompatActivity {

    // for Adapter
    int[] section_icons;
    String[] section_names;
    Class[] section_classes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewAdmin);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_options);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_menu_dashboard) {
                sendToActivity(Dashboard.class);
                return true;
            }
            if (item.getItemId() == R.id.bottom_menu_user_list) {
                sendToActivity(User_List.class);
                return true;
            }
            return true;
        });

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_admin_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        load_Sections();
        optionMenu_RecycleView_Adapter adapter = new optionMenu_RecycleView_Adapter(section_names,section_icons,section_classes,this,"none");
        recyclerView.setAdapter(adapter);
    }

    public void load_Sections(){
        section_names = new String[]{"Log Out"};
        section_icons = new int[]{R.drawable.ic_logout};
        section_classes = new Class[]{login.class};
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}