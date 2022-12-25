package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tradoid.fragments.Portfolio;
import com.example.tradoid.fragments.Stock;

import com.example.tradoid.Adapters.Stock_Market_TabsAdapter;
import com.example.tradoid.Data_handling.stock_view_model;
import com.example.tradoid.fragments.Watchlist;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class Stock_Market extends AppCompatActivity {

    SearchView searchView;
    stock_view_model view_model;
    ViewPager2 viewPager2;

    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);

        // get User ID
        if (getIntent().hasExtra("user_ID")){
//            Toast.makeText(this,"Stock Market: "+)
        }

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(stock_view_model.class);

        // Creating a custom Toolbar
        Toolbar stock_market_toolbar = findViewById(R.id.toolbar_stock_market);
        setSupportActionBar(stock_market_toolbar);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_stock_market);

        // Perfom item selected listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_menu_status_pg:
                        sendToActivity(Status_Page.class);
                        return true;
                    case R.id.bottom_menu_profile:
                        sendToActivity(Profile.class);
                        return true;
                    default:
                        return true;
                }
            }
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
        getMenuInflater().inflate(R.menu.user_list_menu,menu);

        // Reference to the menu item for search
        MenuItem searchItem = menu.findItem(R.id.menu_user_search);

        // Reference to the search view itself
        searchView = (SearchView) searchItem.getActionView();

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
                Stock stock_frag = (Stock) getSupportFragmentManager().findFragmentByTag("f0" );
                Watchlist watch_frag = (Watchlist) getSupportFragmentManager().findFragmentByTag("f1");
                Portfolio portfolio_frag = (Portfolio) getSupportFragmentManager().findFragmentByTag("f2");
                // Update the fragments
                if(stock_frag != null && watch_frag != null && portfolio_frag != null) {
                    stock_frag.UpdateAdapter(newText);
                    watch_frag.UpdateAdapter(newText);
                    portfolio_frag.UpdateAdapter(newText);
                }
                else{System.out.println("Cant open frag");}
                return false;
            }
        });

        return true;
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }


}