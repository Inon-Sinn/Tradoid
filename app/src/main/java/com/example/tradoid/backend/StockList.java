package com.example.tradoid.backend;

import java.util.Arrays;
import java.util.List;

public class StockList {
    private final List<Stock> stockList;

    public StockList(Stock[] stockList){
        this.stockList = Arrays.asList(stockList);
    }

    public List<Stock> getStockList() {
        return stockList;
    }
}
