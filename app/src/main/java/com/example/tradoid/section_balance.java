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

import com.example.tradoid.firebase.model.BalanceViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class section_balance extends AppCompatActivity {

    String user_ID;

    // Deposit/Withdraw Action
    String[] actions = {"Deposit","Withdraw"};
    String current_action = null;
    TextInputEditText editText_usd;
    TextView tv_error;
    Float balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_balance);

        // connect to view model
        BalanceViewModel viewModel = new ViewModelProvider(this).get(BalanceViewModel.class);
        TextView tv_balance = findViewById(R.id.balance_tv);

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.balance_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(Profile.class));

        // Connecting to the request button
        Button request_btn = findViewById(R.id.button_send_request_balance);
        request_btn.setOnClickListener(v -> request(viewModel, tv_balance));

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_balance);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> current_action = parent.getItemAtPosition(position).toString());

        // Connecting the Text Edit for USD and Coins
        editText_usd = findViewById(R.id.edit_text_usd_balance);

        // Connecting to the Error Text View
        tv_error = findViewById(R.id.tv_error_msg_balance);

        viewModel.reset();

        viewModel.loadBalance(user_ID);

        viewModel.getBalance().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                balance = aFloat;
                tv_balance.setText("$" + aFloat.toString());
            }
        });
    }

    public void request(BalanceViewModel viewModel, TextView tv_balance){
        tv_error.setTextColor(Color.RED);
        String text = String.valueOf(editText_usd.getText());
        if(current_action == null || text.equals("")){
            tv_error.setText(R.string.balance_error_msg);
        }
        else{
            tv_error.setText("");
            if (current_action.equals("Deposit")){
                viewModel.updateBalance(user_ID, balance + Float.parseFloat(text));
            } else if (current_action.equals("Withdraw")){
                if (Float.parseFloat(text) > balance){
                    tv_error.setText("Not enough balance in account!");
                    return;
                } else {
                    viewModel.updateBalance(user_ID, balance - Float.parseFloat(text));
                }
            }
            viewModel.getBalance().observe(this, new Observer<Float>() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onChanged(Float aFloat) {
                    tv_balance.setText("$" + aFloat);
                    tv_error.setTextColor(R.color.tv_error);
                    tv_error.setText(current_action + " successful");
                }
            });
        }
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}