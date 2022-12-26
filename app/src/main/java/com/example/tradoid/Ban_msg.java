package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tradoid.firebase.model.BanMsgViewModel;

public class Ban_msg extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_msg);

        // get User ID
        if (getIntent().hasExtra("user_ID")){userId = getIntent().getStringExtra("user_ID");}

        // get ban msg text area
        TextView tv_ban_msg = findViewById(R.id.ban_msg_tv);

        // connect to view model
        BanMsgViewModel viewModel = new ViewModelProvider(this).get(BanMsgViewModel.class);
        viewModel.reset();

        viewModel.loadBanMsg(userId);
        viewModel.getBanMsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_ban_msg.setText("Reason: " + s);
            }
        });

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.ban_msg_back_arrow);
        tv_back_arrow.setOnClickListener(v -> {
            Intent intent = new Intent(this,Sign_In.class);
            startActivity(intent);
        });
    }
}