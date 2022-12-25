package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tradoid.Adapters.history_RecycleView_Adapter;

public class section_history extends AppCompatActivity {

    String user_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_history);

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.history_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(Profile.class));

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        history_RecycleView_Adapter adapter = new history_RecycleView_Adapter(this);
        recyclerView.setAdapter(adapter);


    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}