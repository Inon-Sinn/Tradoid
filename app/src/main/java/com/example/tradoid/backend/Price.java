package com.example.tradoid.backend;

public class Price {
    private final int day;
    private final double price;

    public Price(int day, double price){
        this.day = day;
        this.price = price;
    }

    public int getDay() {
        return day;
    }

    public double getPrice() {
        return price;
    }
}
