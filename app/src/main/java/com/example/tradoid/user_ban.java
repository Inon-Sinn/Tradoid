package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.tradoid.backend.*;
import com.example.tradoid.firebase.model.BanUserViewModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class user_ban extends AppCompatActivity {

    String[] actions = {"Ban", "Unban"};
    String current_action = null;

    TextView tv_error;
    TextView tv_reason;

    User user;

    Gson gson = new Gson();

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ban);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.user_ban_back_arrow);
        tv_back_arrow.setText(user.getUsername());

        Map<String, String> params = new HashMap<>();
        params.put("user", gson.toJson(user));

        tv_back_arrow.setOnClickListener(v -> sendToActivity(User_Status.class, params));

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_user_ban);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> current_action = parent.getItemAtPosition(position).toString());

        // Connecting to apply Button
        Button apply = findViewById(R.id.button_apply_user_ban);
        apply.setOnClickListener(v -> applyRequest());

        // Connecting to TextViews
        tv_error = findViewById(R.id.tv_error_msg_user_ban);
        tv_reason = findViewById(R.id.reason_tv);
    }

    @SuppressLint("ResourceAsColor")
    public void applyRequest(){
        String msg;
        tv_error.setTextColor(Color.RED);
        if (current_action == null){
            msg = "Please choose Action";
            tv_error.setText(msg);
        } else {
            boolean failed = false;
            if (current_action.equals("Ban")){
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", user.getUserId());
                payload.put("reason", tv_reason.getText().toString());
                Response response = client.sendPost("ban_user", payload);
                if (response.passed()){
                    BanUser banUser = new Gson().fromJson(response.getData(), BanUser.class);
                    if (banUser.isSuccess()){
                        tv_error.setTextColor(R.color.tv_error);
                        String ban_msg = "User banned successfully";
                        tv_error.setText(ban_msg);
                    } else { failed = true; }
                } else { failed = true; }
            } else if (current_action.equals("Unban")){
                Map<String, Object> payload = new HashMap<>();
                payload.put("userId", user.getUserId());
                Response response = client.sendPost("unban_user", payload);
                if (response.passed()){
                    BanUser unbanUser = new Gson().fromJson(response.getData(), BanUser.class);
                    if (unbanUser.isSuccess()){
                        tv_error.setTextColor(R.color.tv_error);
                        String ban_msg = "User unbanned successfully";
                        tv_error.setText(ban_msg);
                    } else { failed = true; }
                } else { failed = true; }
            }
            if (failed) {
                tv_error.setTextColor(Color.RED);
                String ban_msg = "An error has occurred.";
                tv_error.setText(ban_msg);
            }
        }
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