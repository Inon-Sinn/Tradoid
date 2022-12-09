package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.tradoid.Adapters.TabsAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Stock_Market extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);

        // Creating a custom Toolbar
        Toolbar stock_market_toolbar = findViewById(R.id.toolbar_stock_market);
        setSupportActionBar(stock_market_toolbar);

        // Connection to Stock Page TODO only temporary remove later
        Button to_Stock_Page_btn = findViewById(R.id.stock_market_To_stock_page);
        to_Stock_Page_btn.setOnClickListener(v -> sendToActivity(Stock_Page.class));

        // Creating the tab layout and view Pager
        TabLayout tabLayout = findViewById(R.id.tabLayout_stock_market);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager_stock_market); // allows going over tabs using swipes

        // Creating new Tabs adapter - connects tab to fragment
        TabsAdapter tabsAdapter = new TabsAdapter(this);
        viewPager2.setAdapter(tabsAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Objects.requireNonNull(tabLayout.getTabAt(position)).select();
            }
        });

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
            //sendToActivity(Stock_Market.class); Already on this page
            return true;
        }
        else if (item.getItemId() == R.id.menu_status_pg) {
            sendToActivity(Status_Page.class);
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