package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.Response;

public class login extends AppCompatActivity {

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Button that send to Sign in Activity
        Button to_Sign_In_btn = findViewById(R.id.login_To_sign_in);
        to_Sign_In_btn.setOnClickListener(v -> sendToActivity(Sign_In.class));

        // Button that sends to Sign Up Activity
        Button to_Sign_Up_btn = findViewById(R.id.login_To_sign_up);
        to_Sign_Up_btn.setOnClickListener(v -> sendToActivity(Sign_Up.class));

//        Response response = client.sendGet("");
//        System.out.println(response.getData());

    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (!getIntent().hasExtra("login")){
            super.onBackPressed();
        }
    }
}