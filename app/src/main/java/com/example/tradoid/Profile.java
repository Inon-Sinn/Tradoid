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
import com.example.tradoid.firebase.model.ProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    String user_ID;

    // for Adapter
    int[] section_icons;
    String[] section_names;
    Class[] section_classes;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    ImageView profileIMG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        load_Sections();
        optionMenu_RecycleView_Adapter adapter = new optionMenu_RecycleView_Adapter(section_names,section_icons,section_classes,this,user_ID);
        recyclerView.setAdapter(adapter);

        // Creating a Bottom Navigation Bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set Home - Bottom Navigation Bar
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_profile);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.bottom_menu_status_pg) {
                sendToActivity(Status_Page.class);
                return true;
            }
            else if (item.getItemId() == R.id.bottom_menu_stock_market) {
                sendToActivity(Stock_Market.class);
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

        // View Model to access User Data
        ProfileViewModel viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.reset();

        // Load the username and email
        viewModel.loadProfileUsers(user_ID);
        viewModel.getUsername().observe(this, tv_username::setText);
        viewModel.getEmail().observe(this, tv_email::setText);

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
        pickMedia.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build());
    }

    public void load_Sections(){
        section_names = new String[]{"Notification","History","Balance","Log Out"};
        section_icons = new int[]{R.drawable.ic_notification,R.drawable.ic_history,R.drawable.ic_balance,R.drawable.ic_logout};
        section_classes = new Class[]{section_notification.class,section_history.class, section_balance.class, login.class};
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}