package com.example.tradoid.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Stock {
    private final String stockId;
    private final String fullName;
    private final double currentPrice;
    private final double change;

    public Stock(String stockId, String fullName, double currentPrice, double change){
        this.stockId = stockId;
        this.fullName = fullName;
        this.currentPrice = currentPrice;
        this.change = change;
    }

    public String getStockId() {
        return stockId;
    }

    public String getFullName(){
        try {
            return URLDecoder.decode(this.fullName, "UTF-8");
        } catch (UnsupportedEncodingException e){
            return this.fullName;
        }
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getChange() {
        return change;
    }
}
