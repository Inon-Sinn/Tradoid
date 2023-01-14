package com.example.tradoid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

    private static final int GALLERY_REQUEST_CODE = 1000;
    // for Adapter
    int[] section_icons;
    String[] section_names;
    Class[] section_classes;

    ImageView profileIMG;

    User user;

    Gson gson = new Gson();

    public HttpUtils client = new HttpUtils();

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

        optionMenu_RecycleView_Adapter adapter = new optionMenu_RecycleView_Adapter(section_names, section_icons, section_classes, this, params);
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
            } else if (item.getItemId() == R.id.bottom_menu_stock_market) {
                sendToActivity(Stock_Market.class, params);
                return true;
            }
            return true;
        });

//        //Connect to profile_image
        profileIMG = findViewById(R.id.profileImage);
        profileIMG.setOnClickListener(v -> pickProfilePicture());

        // Connect to Text Views for Name and Email
        TextView tv_username = findViewById(R.id.profile_tv_username);
        TextView tv_email = findViewById(R.id.profile_tv_email);

        assert user != null;
        tv_username.setText(user.getUsername());
        tv_email.setText(user.getEmail());


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Response pfpResponse = client.sendGet("load_pfp/" + user.getUserId());
            if (pfpResponse.passed()) {
                PFP pfp = gson.fromJson(pfpResponse.getData(), PFP.class);
                profileIMG.setImageURI(pfp.getPfpPath());
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PackageManager.PERMISSION_GRANTED);
        }
    }

    public void pickProfilePicture(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == GALLERY_REQUEST_CODE){
                assert data != null;
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", user.getUserId());
                payload.put("pfpPath", data.getData().toString());

                Response response = client.sendPost("set_pfp", payload);
                if (response.passed()) {
                    Success success = gson.fromJson(response.getData(), Success.class);
                    if (success.isSuccess()){
                        profileIMG.setImageURI(data.getData());
                    }
                }
            }
        }
    }

    public void load_Sections(){
        section_names = new String[]{"Notification","History","Balance","Log Out"};
        section_icons = new int[]{R.drawable.ic_notification,R.drawable.ic_history,R.drawable.ic_balance,R.drawable.ic_logout};
        section_classes = new Class[]{section_notification.class,section_history.class, section_balance.class, login.class};
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