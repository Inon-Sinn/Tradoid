package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

public class login extends AppCompatActivity {

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
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }


}