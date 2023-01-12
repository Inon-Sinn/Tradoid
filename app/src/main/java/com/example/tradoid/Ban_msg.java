package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Ban_msg extends AppCompatActivity {

    String reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_msg);

        if (getIntent().hasExtra("reason")) {
            reason = getIntent().getStringExtra("reason");
        }

        // get ban msg text area
        TextView tv_ban_msg = findViewById(R.id.ban_msg_tv);

        String msg = "We are sorry to tell you that you have been banned for: " + reason;
        tv_ban_msg.setText(msg);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.ban_msg_back_arrow);
        tv_back_arrow.setOnClickListener(v -> {
            Intent intent = new Intent(this,Sign_In.class);
            startActivity(intent);
        });
    }
}