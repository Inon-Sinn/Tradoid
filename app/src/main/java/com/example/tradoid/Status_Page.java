package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tradoid.Adapters.Stock_Market_RecycleView_Adapter;
import com.example.tradoid.Adapters.status_RecyleView_Adapter;
import com.example.tradoid.Data_handling.Data_Layer;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.Data_handling.user_data;

public class Status_Page extends AppCompatActivity {

    Data_Layer portfolio_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_page);

        // get user_data
        user_data user = new user_data("Temp","Temp",0);

        // Connect to View Model
        stock_view_model view_model = new ViewModelProvider(this).get(stock_view_model.class);
        view_model.setUser(user,"status page");

        // Creating a custom Toolbar
        Toolbar status_page_toolbar = findViewById(R.id.toolbar_status_page);
        setSupportActionBar(status_page_toolbar);

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_status_page);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        status_RecyleView_Adapter adapter = new status_RecyleView_Adapter(this,view_model.getData_list(),user.getStock_amount(),false);
        recyclerView.setAdapter(adapter);

    }

    // Creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return true;
    }

    // Makes the menu items clickable
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_stock_market) {
            sendToActivity(Stock_Market.class);
            return true;
        }
        else if (item.getItemId() == R.id.menu_status_pg) {
            //sendToActivity(Status_Page.class); Already on this page
            return true;
        }
        else if (item.getItemId() == R.id.menu_user_logout) {
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