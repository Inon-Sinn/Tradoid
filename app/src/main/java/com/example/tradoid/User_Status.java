package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class User_Status extends AppCompatActivity {

    // Views with Dynamic values
    TextView tv_name;

    // The Dynamic values
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_status);

        // Creating a custom Toolbar
        Toolbar user_status_toolbar = findViewById(R.id.toolbar_user_status);
        setSupportActionBar(user_status_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.user_status_To_user_list);
        back_arrow.setOnClickListener(v -> sendToActivity(User_List.class));

        // Connecting to the TextViews With Dynamic Values
        tv_name = findViewById(R.id.user_status_name);

        // Get the data form the row it was clicked on
        getData();
        setData();
    }

    // getting the data from the intent
    private void getData(){
        // Check if it received the data
        if (getIntent().hasExtra("name")){
            name = getIntent().getStringExtra("name");
        }else{
            // In Case something went wrong
            Toast.makeText(this,"Error: No data",Toast.LENGTH_SHORT).show();
        }
    }

    // setting the data to our elements(image and text views)
    private void setData(){
        tv_name.setText(name);
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}