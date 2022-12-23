package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Ban_msg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_msg);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.ban_msg_back_arrow);
        tv_back_arrow.setOnClickListener(v -> {
            Intent intent = new Intent(this,Sign_In.class);
            startActivity(intent);
        });
    }
}