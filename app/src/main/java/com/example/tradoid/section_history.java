package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tradoid.Adapters.history_RecycleView_Adapter;
import com.example.tradoid.backend.History;
import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.Response;
import com.example.tradoid.backend.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class section_history extends AppCompatActivity {

    User user;

    Gson gson = new Gson();

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_history);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.history_back_arrow);

        Map<String, String> params = new HashMap<>();
        params.put("user", gson.toJson(user));

        tv_back_arrow.setOnClickListener(v -> sendToActivity(Profile.class, params));

        // Creating the Recycle View - the list
        RecyclerView recyclerView = findViewById(R.id.recyclerView_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Calling the Adapter
        List<String[]> historyList = new ArrayList<>();
        Response response = client.sendGet("get_history/" + user.getUserId());
        if (response.passed()) {
            History history = gson.fromJson(response.getData(), History.class);
            historyList = history.getHistoryFormatted();
        }

        history_RecycleView_Adapter adapter = new history_RecycleView_Adapter(this, historyList, params);
        recyclerView.setAdapter(adapter);
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