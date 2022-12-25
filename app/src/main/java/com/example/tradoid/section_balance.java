package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class section_balance extends AppCompatActivity {

    String user_ID;

    // Deposit/Withdraw Action
    String[] actions = {"Deposit","Withdraw"};
    String current_action = null;
    TextInputEditText editText_usd;
    TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_balance);

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.balance_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(Profile.class));

        // Connecting to the request button
        Button request_btn = findViewById(R.id.button_send_request_balance);
        request_btn.setOnClickListener(v -> request());

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_balance);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> current_action = parent.getItemAtPosition(position).toString());

        // Connecting the Text Edit for USD and Coins
        editText_usd = findViewById(R.id.edit_text_usd_balance);

        // Connecting to the Error Text View
        tv_error = findViewById(R.id.tv_error_msg_balance);
    }

    public void request(){
        String text = String.valueOf(editText_usd.getText());
        if(current_action == null || text.equals("")){
            tv_error.setText(R.string.balance_error_msg);
        }
        else{
            tv_error.setText("");
            Toast.makeText(this,"Send Request",Toast.LENGTH_SHORT).show();
        }
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}