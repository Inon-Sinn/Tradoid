package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tradoid.Adapters.history_RecycleView_Adapter;
import com.example.tradoid.Adapters.profile_RecycleView_Adapter;

public class section_history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_history);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.history_back_arrow);
        tv_back_arrow.setOnClickListener(v -> startActivity(new Intent(this,Profile.class)));

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        history_RecycleView_Adapter adapter = new history_RecycleView_Adapter(this);
        recyclerView.setAdapter(adapter);
    }
}