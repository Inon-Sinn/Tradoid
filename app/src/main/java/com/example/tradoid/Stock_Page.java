package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class Stock_Page extends AppCompatActivity {

    // Views with Dynamic values
    TextView tv_name, tv_full_name, tv_price, tv_price_change;
    ImageView iv_icon;

    // The Dynamic values
    String name, full_name, formerScreen;
    double price, price_change;
    int icon;


    // Buy/Sell Action
    String[] actions = {"Buy","Sell"};
    String current_action;
    double total_value;
    public boolean stopListening = false; // needed so don't have an infinite loop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);

        // Get the data form the row it was clicked on
        getData();

        // Creating a custom Toolbar
        Toolbar stock_page_toolbar = findViewById(R.id.toolbar_stock_page);
        setSupportActionBar(stock_page_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.stock_pg_To_stock_market);
        back_arrow.setOnClickListener(v -> sendToActivity(regocnizeScreen()));

        // Implementing the Bookmark in the Toolbar
        ImageView bookmark = findViewById(R.id.bookmark_stock_pg);
        bookmark.setOnClickListener(v -> Bookmark_stock());

        // Connecting to the TextViews With Dynamic Values
        tv_name = findViewById(R.id.stock_page_tv_name);
        tv_full_name = findViewById(R.id.stock_page_tv_full_name);
        tv_price = findViewById(R.id.stock_page_tv_price);
        tv_price_change = findViewById(R.id.stock_page_tv_price_change);

        // Connecting to the ImageView With Dynamic Values
        iv_icon = findViewById(R.id.stock_page_icon_price_change);

        // set The dynamic views
        setData();

        // Connecting the Buy/Sell button
        Button transaction = findViewById(R.id.button_stock_page);
        transaction.setOnClickListener(v -> create_transaction());

        // Connecting to Action list - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_stock_page);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.stock_page_action_list_item, actions);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();
            current_action = item;
            transaction.setText(item);
        });

        // Connecting to the Text View Total value
        TextView total_value_tv = findViewById(R.id.tv_total_price_stock_page);

        // Connecting the Text Edit for USD and Coins
        TextInputEditText editText_usd = findViewById(R.id.edit_text_USD_stock_page);
        TextInputEditText editText_Stock = findViewById(R.id.edit_text_stocks_stock_page);

        // Text Watcher for the USD value
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
                        DecimalFormat numberFormat = new DecimalFormat("#.000");
                        editText_Stock.setText(numberFormat.format(num/price)); // The amount of Stock that can be bought
                        total_value = num;
                        total_value_tv.setText("Total: " + total_value + " $");
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

        // Text Watcher for the Stock value
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
                        DecimalFormat numberFormat = new DecimalFormat("#.000");
                        editText_usd.setText(numberFormat.format(num * price)); // The amount of Stock that can be bought
                        total_value = num * price;
                        total_value_tv.setText("Total: " + total_value + " $");
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

    }

    // getting the data from the intent
    private void getData(){
        // Check if it received the data
        if (getIntent().hasExtra("name") && getIntent().hasExtra("full_name") && getIntent().hasExtra("price") && getIntent().hasExtra("price_change") && getIntent().hasExtra("icon")){
            name = getIntent().getStringExtra("name");
            full_name = getIntent().getStringExtra("full_name");
            price = getIntent().getDoubleExtra("price",0);
            price_change = getIntent().getDoubleExtra("price_change",0);
            icon = getIntent().getIntExtra("icon",1);

        }else{
            // In Case something went wrong
            Toast.makeText(this,"Error: No data",Toast.LENGTH_SHORT).show();
        }

        if (getIntent().hasExtra("former Screen"))
            formerScreen = getIntent().getStringExtra("former Screen");
    }

    // setting the data to our elements(image and text views)
    private void setData(){
        tv_name.setText(name);
        tv_full_name.setText(full_name);
        tv_price.setText(String.valueOf(price));
        tv_price_change.setText(String.valueOf(price_change));
        iv_icon.setImageResource(icon);
    }

    // The Function called when we Bookmark a stock
    public void Bookmark_stock(){
        Toast.makeText(getApplicationContext(),"Bookmark: Still not implemented",Toast.LENGTH_SHORT).show();
    }

    public void create_transaction(){
        Toast.makeText(getApplicationContext(),"Still not implemented",Toast.LENGTH_SHORT).show();
    }

    public Class regocnizeScreen(){
        if (formerScreen.equals("Stock_Market"))
            return Stock_Market.class;
        if (formerScreen.equals("Status_Page"))
            return Status_Page.class;
        return null;
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}