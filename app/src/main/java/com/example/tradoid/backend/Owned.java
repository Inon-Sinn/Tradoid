package com.example.tradoid.backend;

public class Owned {
    private final Stock stock;
    private final double amount;

    public Owned(Stock stock, double amount){
        this.stock = stock;
        this.amount = amount;
    }

    public Stock getStock() {
        return stock;
    }

    public double getAmount() {
        return amount;
    }
}
