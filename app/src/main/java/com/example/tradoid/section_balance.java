package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tradoid.backend.*;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class section_balance extends AppCompatActivity {

    // Deposit/Withdraw Action
    String[] actions = {"Deposit", "Withdraw"};
    String current_action = null;
    TextInputEditText editText_usd;
    TextView tv_error;

    User user;

    Gson gson = new Gson();

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_balance);

        TextView tv_balance = findViewById(R.id.balance_tv);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.balance_back_arrow);

        tv_back_arrow.setOnClickListener(v -> {
            Map<String, String> params = new HashMap<>();
            params.put("user", gson.toJson(user));

            sendToActivity(Profile.class, params);
        });

        // Connecting to the request button
        Button request_btn = findViewById(R.id.button_send_request_balance);
        request_btn.setOnClickListener(v -> request(tv_balance));

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_balance);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> current_action = parent.getItemAtPosition(position).toString());

        // Connecting the Text Edit for USD and Coins
        editText_usd = findViewById(R.id.edit_text_usd_balance);

        // Connecting to the Error Text View
        tv_error = findViewById(R.id.tv_error_msg_balance);

        tv_balance.setText("$" + user.getBalance());
    }

    @SuppressLint("ResourceAsColor")
    public void request(TextView tv_balance){
        tv_error.setTextColor(Color.RED);
        String text = String.valueOf(editText_usd.getText());
        if(current_action == null || text.equals("")){
            tv_error.setText(R.string.balance_error_msg);
        }
        else{
            tv_error.setText("");
            Map<String, Object> payload = new HashMap<>();
            payload.put("userId", user.getUserId());
            if (current_action.equals("Deposit")) {
                payload.put("amount", user.getBalance() + Double.parseDouble(text));
                Response response = client.sendPost("update_balance", payload);
                if (response.passed()) {
                    Balance balance = new Gson().fromJson(response.getData(), Balance.class);
                    user.setBalance(balance.getBalance());
                    tv_balance.setText("$" + user.getBalance());
                    tv_error.setTextColor(R.color.tv_error);
                    tv_error.setText("Deposit successful");
                }
            } else if (current_action.equals("Withdraw")) {
                if (Double.parseDouble(text) > user.getBalance()) {
                    tv_error.setText("Not enough balance in account!");
                    return;
                }
                payload.put("amount", user.getBalance() - Double.parseDouble(text));
                Response response = client.sendPost("update_balance", payload);
                if (response.passed()) {
                    Balance balance = new Gson().fromJson(response.getData(), Balance.class);
                    user.setBalance(balance.getBalance());
                    tv_balance.setText("$" + user.getBalance());
                    tv_error.setTextColor(R.color.tv_error);
                    tv_error.setText("Withdraw successful");
                }
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