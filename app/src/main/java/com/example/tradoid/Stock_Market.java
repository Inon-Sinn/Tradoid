package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.example.tradoid.backend.User;
import com.example.tradoid.fragments.Portfolio;
import com.example.tradoid.fragments.Stocks;
import com.example.tradoid.Adapters.Stock_Market_TabsAdapter;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.fragments.Bookmarks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Stock_Market extends AppCompatActivity {

    SearchView searchView;
    stock_view_model view_model;
    ViewPager2 viewPager2;

    User user;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(stock_view_model.class);

        // Creating a custom Toolbar
        Toolbar stock_market_toolbar = findViewById(R.id.toolbar_stock_market);
        setSupportActionBar(stock_market_toolbar);

        // custom action bar

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_stock_market);

        // Perform item selected listener

        Map<String, String> params = new HashMap<>();
        params.put("user", gson.toJson(user));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_menu_status_pg) {
                sendToActivity(Status_Page.class, params);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_menu_profile) {
                sendToActivity(Profile.class, params);
                return true;
            }
            return true;
        });

        // Creating the tab layout and view Pager
        TabLayout tabLayout = findViewById(R.id.tabLayout_stock_market);
        viewPager2 = findViewById(R.id.view_pager_stock_market); // allows going over tabs using swipes

        // Ensures us that all fragments are created so the first time search works
        viewPager2.setOffscreenPageLimit(3);

        // Creating new Tabs adapter - connects tab to fragment
        Stock_Market_TabsAdapter stockMarketTabsAdapter = new Stock_Market_TabsAdapter(this);
        viewPager2.setAdapter(stockMarketTabsAdapter);

        // Define what to do in case a tab was Selected
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

        // Allows to switch tabs by swiping
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
        getMenuInflater().inflate(R.menu.user_search_menu,menu);

        // Reference to the menu item for search
        MenuItem searchItem = menu.findItem(R.id.menu_user_search);

        // Reference to the search view itself
        searchView = (SearchView) searchItem.getActionView();

        // Removing the search view edit text line
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(R.drawable.default_background);

        // change the background on touch
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> searchView.setBackground(getDrawable(R.drawable.searchview_background)));
        searchView.setBackground(getDrawable(R.drawable.default_background));

        // change the done button on the keyboard
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // Listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //fragment connection
                updateFragmentsAdapter(newText);
                return false;
            }
        });

        return true;
    }

    // Used by fragments
    public User getUser(){
        return user;
    }

    // Updates the Fragments ViewModel
    public void updateFragmentsAdapter(String newText){
        Stocks stock_frag = (Stocks) getSupportFragmentManager().findFragmentByTag("f0" );
        Bookmarks watch_frag = (Bookmarks) getSupportFragmentManager().findFragmentByTag("f1");
        Portfolio portfolio_frag = (Portfolio) getSupportFragmentManager().findFragmentByTag("f2");
        // Update the fragments
        if(stock_frag != null && watch_frag != null && portfolio_frag != null) {
            stock_frag.UpdateAdapter(newText);
            watch_frag.UpdateAdapter(newText);
            portfolio_frag.UpdateAdapter(newText);
        }
        else{System.out.println("Cant open frag");}
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