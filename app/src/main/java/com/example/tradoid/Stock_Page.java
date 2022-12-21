package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Stock_Page extends AppCompatActivity {

    // TODO put in oncreate and will have to be removed
    ImageView mainImageView;
    TextView title,subTitle;

    String data1,data2,data3,data4;
    int myImage;

    String[] actions = {"Buy","Sell"};
    String current_action;
    double stock_in_usd = 10;
    double total_value;
    public boolean stopListening = false; // needed so don't have an infinite loop

    // ------------------------------ TODO can stay
    TextView toolbar_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);

        // Creating a custom Toolbar
        Toolbar stock_page_toolbar = findViewById(R.id.toolbar_stock_page);
        setSupportActionBar(stock_page_toolbar);

        // Connecting to TextView in Toolbar
        toolbar_Title = findViewById(R.id.stock_pg_name);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.stock_pg_To_stock_market);
        back_arrow.setOnClickListener(v -> sendToActivity(Stock_Market.class));

        // Implementing the Bookmark in the Toolbar
        ImageView bookmark = findViewById(R.id.bookmark_stock_pg);
        bookmark.setOnClickListener(v -> Bookmark_stock());

        // Connecting the Buy/Sell button
        Button transaction = findViewById(R.id.button_stock_page);
        transaction.setOnClickListener(v -> create_transaction());

        // Connecting to Action list - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_stock_page);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<String>(this,R.layout.stock_page_action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                current_action = item;
                transaction.setText(item);
            }
        });

        // Connecting to the Text View Total value
        TextView total_value_tv = findViewById(R.id.tv_total_price_stock_page);

        // Connecting the Text Edit for USD and Coins
        TextInputEditText editText_usd = findViewById(R.id.edit_text_USD_stock_page);
        TextInputEditText editText_Stock = findViewById(R.id.edit_text_stocks_stock_page);

        editText_usd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!stopListening) {
                    stopListening = true;
                    if (s.length() != 0) {
                        double num = Double.parseDouble(s.toString());
                        editText_Stock.setText(String.valueOf(num / stock_in_usd)); // The amount of Stock that can be bought
                        total_value = num;
                        total_value_tv.setText("Total: " + String.valueOf(total_value) + " $");
                    } else {
                        editText_Stock.setText(""); // The amount of Stock that can be bought
                        total_value = 0;
                        total_value_tv.setText("Total:");
                    }
                }
                stopListening = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editText_Stock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!stopListening) {
                    stopListening = true;
                    if (s.length() != 0) {
                        double num = Double.parseDouble(s.toString());
                        editText_usd.setText(String.valueOf(num * stock_in_usd)); // The amount of Stock that can be bought
                        total_value = num * stock_in_usd;
                        total_value_tv.setText("Total: " + String.valueOf(total_value) + " $");
                    } else {
                        editText_usd.setText(""); // The amount of Stock that can be bought
                        total_value = 0;
                        total_value_tv.setText("Total:");
                    }
                }
                stopListening = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        //TODO all from here will be changed
        // Creating the image view and textViews
        mainImageView = findViewById(R.id.stock_page_image);
        title = findViewById(R.id.stock_page_title);
        subTitle = findViewById(R.id.stock_page_subTitle);


        // Get the data form the row it was clicked on
        getData(); //TODO should probably put at the beginning to avoid bugs
        setData();
    }

    // getting the data from the intent TODO needs to be changed
    private void getData(){
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") && getIntent().hasExtra("data2") && getIntent().hasExtra("data3") && getIntent().hasExtra("data4")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data3 = getIntent().getStringExtra("data1");
            data4 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage",1);
        }else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    // setting the data to our elements(image and text views) TODO needs to be changed
    private void setData(){
        title.setText(data1);
        subTitle.setText(data2);
        mainImageView.setImageResource(myImage);
        // set The Page Title
        toolbar_Title.setText(data1);

    }

    // The Function called when we Bookmark a stock
    public void Bookmark_stock(){
        Toast.makeText(getApplicationContext(),"Bookmark: Still not implemented",Toast.LENGTH_SHORT).show();
    }

    public void create_transaction(){
        Toast.makeText(getApplicationContext(),"Still not implemented",Toast.LENGTH_SHORT).show();
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}