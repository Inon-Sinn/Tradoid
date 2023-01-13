package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tradoid.backend.*;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stock_Page extends AppCompatActivity {

    // Buy/Sell Action
    String[] actions = {"Buy", "Sell"};
    String current_action;
    public boolean stopListening = false; // needed so don't have an infinite loop

    User user;
    Stock stock;
    double price;
    double amount;

    String formerScreen;

    Gson gson = new Gson();

    public HttpUtils client = new HttpUtils();

    // bottom sheet dialog
    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);

        if (getIntent().hasExtra("user")) {
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        }

        if (getIntent().hasExtra("stock")) {
            stock = gson.fromJson(getIntent().getStringExtra("stock"), Stock.class);
        }

        if (getIntent().hasExtra("formerScreen")) {
            formerScreen = getIntent().getStringExtra("formerScreen");
        }

        // Creating a custom Toolbar
        Toolbar stock_page_toolbar = findViewById(R.id.toolbar_stock_page);
        setSupportActionBar(stock_page_toolbar);

        // Implementing the Back arrow in the Toolbar
        TextView back_arrow = findViewById(R.id.stock_page_status_back_arrow);

        back_arrow.setOnClickListener(v -> {
            Map<String, String> params = new HashMap<>();
            params.put("user", gson.toJson(user));
            params.put("stock", gson.toJson(stock));
            sendToActivity(recognizeScreen(), params);
        });

        // Implementing the Bookmark in the Toolbar
        ImageView bookmark = findViewById(R.id.bookmark_stock_pg);
        bookmark.setOnClickListener(v -> Bookmark_stock());

        // Connecting to the TextViews With Dynamic Values
        TextView tv_name = findViewById(R.id.stock_page_tv_name);
        TextView tv_full_name = findViewById(R.id.stock_page_tv_full_name);
        TextView tv_price = findViewById(R.id.stock_page_tv_price);
        TextView tv_price_change = findViewById(R.id.stock_page_tv_price_change);

        // set The dynamic views
        setData(tv_name,tv_full_name,tv_price,tv_price_change);

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

        // Connecting the Text Edit for USD and Coins
        TextInputEditText editText_usd = findViewById(R.id.edit_text_USD_stock_page);
        TextInputEditText editText_Stock = findViewById(R.id.edit_text_stocks_stock_page);

        // Text Watcher for the USD and Stock value
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
                        double num;
                        try {
                            num = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            num = 0;
                        }
                        DecimalFormat numberFormat = new DecimalFormat("#.000");
                        price = num;
                        amount = price/stock.getCurrentPrice();
                        editText_Stock.setText(numberFormat.format(amount)); // The amount of Stock that can be bought
                    } else {
                        price = 0;
                        amount = 0;
                        editText_Stock.setText(""); // The amount of Stock that can be bought
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
                        double num;
                        try {
                            num = Double.parseDouble(s.toString());
                        } catch (NumberFormatException e) {
                            num = 0;
                        }
                        DecimalFormat numberFormat = new DecimalFormat("#.000");
                        amount = num;
                        price = amount * stock.getCurrentPrice();
                        editText_usd.setText(numberFormat.format(price)); // The amount of Stock that can be bought
                    } else {
                        amount = 0;
                        price = 0;
                        editText_usd.setText(""); // The amount of Stock that can be bought
                    }
                }
                stopListening = false;
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Connecting to the background lightening
        View bg_lighting = findViewById(R.id.backgroundLight);
        bg_lighting.setVisibility(View.GONE);

        // Connecting to the Bottom sheet itself and its state
        ConstraintLayout mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);

        // Connecting to the Title and its icon
        TextView title = findViewById(R.id.bottom_sheet_title);
        ImageView icon = findViewById(R.id.bottom_sheet_title_icon);

        // OnClick Listeners to pull up/down the Bottom sheet Dialog
        title.setOnClickListener(v -> bottomSheetClick());
        icon.setOnClickListener(v -> bottomSheetClick());

        //When the bottom sheet get pulled up - darkens the background and changes the icons direction
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    bg_lighting.setVisibility(View.GONE);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                icon.setRotation(slideOffset * 180);
                bg_lighting.setVisibility(View.VISIBLE);
                bg_lighting.setAlpha(slideOffset);
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
        rightAxis.setTextColor(R.color.tv_error);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = candleStickChart.getLegend();
        l.setEnabled(false);

        // Creating Data points
        Response response = client.sendGet("get_stock_chart/" + stock.getStockId());
        if (response.passed()){
            StockChart stockChart = new Gson().fromJson(response.getData(), StockChart.class);
            ArrayList<CandleEntry> yvalCandleStick = new ArrayList<>();
            for (int i = 0; i < stockChart.getHigh().size(); i++){
                yvalCandleStick.add(new CandleEntry(i,
                        (float) stockChart.getHigh().get(i).getPrice(),
                        (float) stockChart.getLow().get(i).getPrice(),
                        (float) stockChart.getOpen().get(i).getPrice(),
                        (float) stockChart.getClose().get(i).getPrice()));
            }

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

        }

        //---------------------------------------------------------------------------------------------------------------------------------------
    }

    // An Auxiliary function that pulls up/down the Bottom sheet dialog when The OnClick listeners were activated
    public void bottomSheetClick(){
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }else{
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    // setting the data to our elements(image and text views)
    private void setData(TextView tv_name, TextView tv_full_name, TextView tv_price,TextView tv_price_change){
        tv_name.setText(stock.getStockId());
        tv_full_name.setText(stock.getFullName());
        tv_price.setText(String.valueOf(stock.getCurrentPrice()));
        if(stock.getChange()>0){
            tv_price_change.setText("+" + stock.getChange());
            tv_price_change.setTextColor(Color.GREEN);
        }
        else {
            tv_price_change.setText(String.valueOf(stock.getChange()));
            tv_price_change.setTextColor(Color.RED);
        }


    }

    // The Function called when we Bookmark a stock
    public void Bookmark_stock(){
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getUserId());
        payload.put("stockId", stock.getStockId());

        Response response = client.sendPost("bookmark_stock", payload);
        if (response.passed()){
            Success success = gson.fromJson(response.getData(), Success.class);
            if (success.isSuccess()){
                Toast.makeText(getApplicationContext(),stock.getStockId() + " bookmarked",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),stock.getStockId() + " is no longer bookmarked",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void create_transaction(){
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getUserId());
        payload.put("stockId", stock.getStockId());
        payload.put("price", price);
        payload.put("amount", amount);

        DecimalFormat numberFormat = new DecimalFormat("#.000");

        if (current_action == null){
            Toast.makeText(getApplicationContext(),
                    "Please choose an action",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (current_action.equals("Buy")){
            Response response = client.sendPost("buy", payload);
            if (response.passed()){
                Toast.makeText(getApplicationContext(),
                        "Bought " + numberFormat.format(amount) + " stocks of " + stock.getStockId() + " for $" + numberFormat.format(price),
                        Toast.LENGTH_SHORT).show();
                Balance balance = gson.fromJson(response.getData(), Balance.class);
                user.setBalance(balance.getBalance());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Not enough money in account",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (current_action.equals("Sell")){
            Response response = client.sendPost("sell", payload);
            if (response.passed()){
                Toast.makeText(getApplicationContext(),
                        "Sold " + numberFormat.format(amount) + " stocks of " + stock.getStockId() + " for $" + numberFormat.format(price),
                        Toast.LENGTH_SHORT).show();
                Balance balance = gson.fromJson(response.getData(), Balance.class);
                user.setBalance(balance.getBalance());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Not enough stocks in account",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Class recognizeScreen(){
        if (formerScreen.equals("Stock_Market"))
            return Stock_Market.class;
        if (formerScreen.equals("Status_Page"))
            return Status_Page.class;
        return null;
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