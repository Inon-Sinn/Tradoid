package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.tradoid.Adapters.optionMenu_RecycleView_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class admin_options extends AppCompatActivity {

    // for Adapter
    int[] section_icons;
    String[] section_names;
    Class[] section_classes;

    String adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        if (getIntent().hasExtra("adminId")) {
            adminId = getIntent().getStringExtra("adminId");
        }

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewAdmin);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_options);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Map<String, String> params = new HashMap<>();
            params.put("adminId", adminId);
            if (item.getItemId() == R.id.bottom_menu_dashboard) {
                ProgressDialog.show(this, "Loading Data", "please wait");
                sendToActivity(Dashboard.class, params);
                return true;
            }
            if (item.getItemId() == R.id.bottom_menu_user_list) {
                sendToActivity(User_List.class, params);
                return true;
            }
            return true;
        });

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_admin_options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        load_Sections();

        Map<String, String> params = new HashMap<>();
        params.put("adminId", adminId);

        optionMenu_RecycleView_Adapter adapter = new optionMenu_RecycleView_Adapter(section_names,section_icons,section_classes,this,params);
        recyclerView.setAdapter(adapter);
    }

    public void load_Sections(){
        section_names = new String[]{"Create Admin","Log Out"};
        section_icons = new int[]{R.drawable.ic_create_admin,R.drawable.ic_logout};
        section_classes = new Class[]{CreateAdmin.class,login.class};
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