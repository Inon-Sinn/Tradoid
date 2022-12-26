package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.example.tradoid.Adapters.User_List_TabsAdapter;
import com.example.tradoid.Data_handling.user_view_model;
import com.example.tradoid.fragments.Banned_users;
import com.example.tradoid.fragments.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class User_List extends AppCompatActivity {

    SearchView searchView;
    user_view_model view_model;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Connect to View Model
        view_model = new ViewModelProvider(this).get(user_view_model.class);

        // Creating a custom Toolbar
        Toolbar user_list_toolbar = findViewById(R.id.toolbar_user_list);
        setSupportActionBar(user_list_toolbar);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationViewAdmin);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_user_list);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_menu_dashboard) {
                sendToActivity(Dashboard.class);
                return true;
            }
            return true;
        });

        // Creating the tab layout and view Pager
        TabLayout tabLayout = findViewById(R.id.tabLayout_user_list);
        viewPager2 = findViewById(R.id.view_pager_user_list); // allows going over tabs using swipes

        // Ensures us that all fragments are created so the first time search works
        viewPager2.setOffscreenPageLimit(2);

        // Creating new Tabs adapter - connects tab to fragment
        User_List_TabsAdapter user_list_tabsAdapter = new User_List_TabsAdapter(this);
        viewPager2.setAdapter(user_list_tabsAdapter);

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
        getMenuInflater().inflate(R.menu.admin_search_menu,menu);

        // Reference to the menu item for search
        MenuItem searchItem = menu.findItem(R.id.menu_admin_search);

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
                Users users_frag = (Users)getSupportFragmentManager().findFragmentByTag("f0");
                Banned_users banned_users_frag = (Banned_users)getSupportFragmentManager().findFragmentByTag("f1");
                if(users_frag != null && banned_users_frag != null) {
                    users_frag.UpdateAdapter(newText);
                    banned_users_frag.UpdateAdapter(newText);
                }
                else{
                    System.out.println("Cant open Users fragment");
                }
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