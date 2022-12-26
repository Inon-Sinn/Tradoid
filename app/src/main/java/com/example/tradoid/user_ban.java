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

import com.example.tradoid.firebase.model.BanUserViewModel;

public class user_ban extends AppCompatActivity {

    String user_ID,username;

    String[] actions = {"Ban","Unban"};
    String current_action = null;

    TextView tv_error;
    TextView tv_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ban);

        // get User ID
        if (getIntent().hasExtra("user_ID")){
            user_ID = getIntent().getStringExtra("user_ID");
            Toast.makeText(this,user_ID,Toast.LENGTH_SHORT).show();
        }

        // get User name
        if (getIntent().hasExtra("name")){
            username = getIntent().getStringExtra("name");
            Toast.makeText(this,username,Toast.LENGTH_SHORT).show();
        }


        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.user_ban_back_arrow);
        tv_back_arrow.setText(username);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(User_Status.class));

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_user_ban);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> current_action = parent.getItemAtPosition(position).toString());

        // connecting to view model
        BanUserViewModel viewModel = new ViewModelProvider(this).get(BanUserViewModel.class);
        viewModel.reset();

        // Connecting to apply Button
        Button apply = findViewById(R.id.button_apply_user_ban);
        apply.setOnClickListener(v -> applyRequest(viewModel));

        // Connecting to TextViews
        tv_error = findViewById(R.id.tv_error_msg_user_ban);
        tv_reason = findViewById(R.id.reason_tv);
    }

    public void applyRequest(BanUserViewModel viewModel){
        tv_error.setTextColor(Color.RED);
        if (current_action == null){
            tv_error.setText("Please choose Action");
        } else {
            if (current_action.equals("Ban")){
                viewModel.banUser(user_ID, tv_reason.getText().toString());
                viewModel.getFinishedBan().observe(this, new Observer<Boolean>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            tv_error.setTextColor(R.color.tv_error);
                            tv_error.setText("User banned successfully");
                        }
                    }
                });
            } else if (current_action.equals("Unban")){
                viewModel.unbanUser(user_ID);
                viewModel.getFinishedUnban().observe(this, new Observer<Boolean>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean){
                            tv_error.setTextColor(R.color.tv_error);
                            tv_error.setText("User unbanned successfully");
                        }
                    }
                });
            }
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