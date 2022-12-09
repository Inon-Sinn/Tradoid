package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Stock_Page extends AppCompatActivity {

    // TODO put in oncreate
    ImageView mainImageView;
    TextView title,subTitle;

    String data1,data2;
    int myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);

        // Creating a custom Toolbar
        Toolbar stock_page_toolbar = findViewById(R.id.toolbar_stock_page);
        setSupportActionBar(stock_page_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.stock_pg_To_stock_market);
        back_arrow.setOnClickListener(v -> sendToActivity(Stock_Market.class));

        // Creating the image view and textViews
        mainImageView = findViewById(R.id.stock_page_image);
        title = findViewById(R.id.stock_page_title);
        subTitle = findViewById(R.id.stock_page_subTitle);

        getData();
        setData();
    }

    // getting the data from the intent
    private void getData(){
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage",1);
        }else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    // setting the data to our elements(image and text views)
    private void setData(){
        title.setText(data1);
        title.setText(data2);
        mainImageView.setImageResource(myImage);
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}