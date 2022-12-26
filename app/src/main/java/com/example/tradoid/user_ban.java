package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class user_ban extends AppCompatActivity {

    String user_ID,username;

    String[] actions = {"Ban","unBan"};
    String current_action = null;

    TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ban);

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // get User name
        if (getIntent().hasExtra("user_ID")){username = getIntent().getStringExtra("name");}

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.user_ban_back_arrow);
        tv_back_arrow.setText(username);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(User_Status.class));

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_user_ban);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> current_action = parent.getItemAtPosition(position).toString());

        // Connecting to apply Button
        Button apply = findViewById(R.id.button_apply_user_ban);
        apply.setOnClickListener(v -> applyRequest());

        // Connecting to error Text view
        tv_error = findViewById(R.id.tv_error_msg_user_ban);
    }

    public void applyRequest(){
        if (current_action == null){
            tv_error.setText("Please choose Action");
        }
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("name",username);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}