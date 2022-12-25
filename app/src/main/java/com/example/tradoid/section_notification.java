package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class section_notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_notification);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.notification_back_arrow);
        tv_back_arrow.setOnClickListener(v -> startActivity(new Intent(this,Profile.class)));


    }
}