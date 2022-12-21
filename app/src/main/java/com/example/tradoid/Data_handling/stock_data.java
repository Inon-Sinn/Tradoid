package com.example.tradoid.Data_handling;

import com.example.tradoid.R;

public class stock_data {

    String name;
    String full_name;
    int icon;
    double total_Price;
    double price_change;

    public stock_data(String name, String full_name, double total_Price, double price_change) {
        this.name = name;
        this.full_name = full_name;
        this.total_Price = total_Price;
        this.price_change = price_change;
        if (price_change >= 0){
            this.icon = R.drawable.ic_green_arrow_upward;
        }
        else{
            this.icon =R.drawable.ic_red_arrow_downward;
        }
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

    public int getIcon() {
        return icon;
    }

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
