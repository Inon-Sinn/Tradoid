package com.example.tradoid;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tradoid.Adapters.optionMenu_RecycleView_Adapter;
import com.example.tradoid.backend.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    // for Adapter
    int[] section_icons;
    String[] section_names;
    Class[] section_classes;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    ImageView profileIMG;

    User user;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        load_Sections();

        Map<String, String> params = new HashMap<>();
        params.put("user", gson.toJson(user));

        optionMenu_RecycleView_Adapter adapter = new optionMenu_RecycleView_Adapter(section_names,section_icons,section_classes,this, params);
        recyclerView.setAdapter(adapter);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_profile);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.bottom_menu_status_pg) {
                sendToActivity(Status_Page.class, params);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_menu_stock_market) {
                sendToActivity(Stock_Market.class, params);
                return true;
            }
            return true;
        });

//        //Connect to profile_image
        profileIMG = findViewById(R.id.profileImage);
        profileIMG.setOnClickListener(v -> loadFromGallary());

        // Connect to Text Views for Name and Email
        TextView tv_username = findViewById(R.id.profile_tv_username);
        TextView tv_email = findViewById(R.id.profile_tv_email);

        assert user != null;
        tv_username.setText(user.getUsername());
        tv_email.setText(user.getEmail());

        pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        profileIMG.setImageURI(uri);
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

    }

    public void loadFromGallary( ){
        // Works but shows error, its a new feature that came out just 2 months ago so it still has bugs
        // Thank you Inon, I stated panicking lmao
        pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
    }

    public void load_Sections(){
        section_names = new String[]{"Notification","History","Balance","Log Out","test"};
        section_icons = new int[]{R.drawable.ic_notification,R.drawable.ic_history,R.drawable.ic_balance,R.drawable.ic_logout,R.drawable.ic_menu};
        section_classes = new Class[]{section_notification.class,section_history.class, section_balance.class, login.class, test_bottom_sheet_dialog.class};
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