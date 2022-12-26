package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Stock_Page extends AppCompatActivity {

    String user_ID;

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

        // get User ID
        if (getIntent().hasExtra("user_ID")){ user_ID = getIntent().getStringExtra("user_ID");}

        // Get the data form the row it was clicked on
        getData();

        // Creating a custom Toolbar
        Toolbar stock_page_toolbar = findViewById(R.id.toolbar_stock_page);
        setSupportActionBar(stock_page_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.stock_pg_To_stock_market);
        back_arrow.setOnClickListener(v -> sendToActivity(recognizeScreen()));

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

        // Connecting to Action - Buy/Sell
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoComplete_tv_stock_page);
        ArrayAdapter<String> adapterItems = new ArrayAdapter<>(this, R.layout.action_list_item, actions);
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


        // -----------------------------In Testing ----------------------------------------------------------------------------------
        // https://medium.com/@neerajmoudgil/candlestick-chart-using-philjay-mpandroidchart-library-how-to-bf657ddf3a28

        // Candle chart
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        CandleStickChart candleStickChart = findViewById(R.id.candle_stick_chart_stock_page);
        candleStickChart.setHighlightPerDragEnabled(true);

        // Customizing Candle chart
        candleStickChart.setDrawBorders(true);
        candleStickChart.setBorderColor(getResources().getColor(R.color.gray));

        YAxis yAxis = candleStickChart.getAxisLeft();
        YAxis rightAxis = candleStickChart.getAxisRight();
        yAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        candleStickChart.requestDisallowInterceptTouchEvent(true);

        XAxis xAxis = candleStickChart.getXAxis();

        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(false);
        rightAxis.setTextColor(Color.WHITE);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = candleStickChart.getLegend();
        l.setEnabled(false);

        // Creating Data points
        ArrayList<CandleEntry> yvalCandleStick = new ArrayList<>();
        yvalCandleStick.add(new CandleEntry((float) 0, (float) 225.0, (float) 219.84, (float) 224.94, (float) 221.07));
        yvalCandleStick.add(new CandleEntry((float)1, (float) 228.35, (float)222.57, (float)223.52, (float)226.41));
        yvalCandleStick.add(new CandleEntry((float)2,(float)226.84,(float)222.52,(float)225.75,(float)223.84));
        yvalCandleStick.add(new CandleEntry((float)3, (float)222.95, (float)217.27, (float)222.15, (float)217.88));

        // Creating and getting dataSet
        CandleDataSet set1 = new CandleDataSet(yvalCandleStick,"DataSet1");
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(R.color.gray));
        set1.setShadowWidth(0.8f);
        set1.setDecreasingColor(getResources().getColor(R.color.red));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(getResources().getColor(R.color.money_green));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.LTGRAY);
        set1.setDrawValues(false);

        // create a data object with the datasets
        CandleData data = new CandleData(set1);

        // set data
        candleStickChart.setData(data);
        candleStickChart.invalidate();

        //---------------------------------------------------------------------------------------------------------------------------------------
    }

    // getting the data from the intent
    private void getData(){
        // Check if it received the data
        if (getIntent().hasExtra("name") && getIntent().hasExtra("full_name") && getIntent().hasExtra("price") && getIntent().hasExtra("price_change")){
            name = getIntent().getStringExtra("name");
            full_name = getIntent().getStringExtra("full_name");
            price = getIntent().getDoubleExtra("price",0);
            price_change = getIntent().getDoubleExtra("price_change",0);
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

    public Class recognizeScreen(){
        if (formerScreen.equals("Stock_Market"))
            return Stock_Market.class;
        if (formerScreen.equals("Status_Page"))
            return Status_Page.class;
        return null;
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}