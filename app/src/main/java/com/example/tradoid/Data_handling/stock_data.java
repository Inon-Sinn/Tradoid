package com.example.tradoid.Data_handling;

import androidx.annotation.NonNull;

public class stock_data {

    String name;
    String full_name;
    double total_Price;
    double price_change;

    public stock_data(String name, String full_name, double total_Price, double price_change) {
        this.name = name;
        this.full_name = full_name;
        this.total_Price = total_Price;
        this.price_change = price_change;
    }

    public String toFilterBy(){
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getFull_name() {
        return full_name;
    }

    public double getTotal_Price() {
        return total_Price;
    }

    public double getPrice_change() {
        return price_change;
    }

    @NonNull
    @Override
    public String toString() {
        return "stock_data{" +
                "name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", total_Price=" + total_Price +
                ", price_change=" + price_change +
                '}';
    }
}
