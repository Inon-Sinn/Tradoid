package com.example.tradoid.Data_handling;

import androidx.annotation.NonNull;

import java.util.List;

public class user_data {

    String name;
    String email;
    double total_worth;
    List<double[]> stock_amount;

    public user_data(String name, String email, double total_amount) {
        this.name = name;
        this.email = email;
        this.total_worth = total_amount;
    }

    public String toFilterBy(){
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getTotal_worth() {
        return total_worth;
    }

    public List<double[]> getStock_amount() {
        return stock_amount;
    }

    public void setStock_amount(List<double[]> stock_amount) {
        this.stock_amount = stock_amount;
    }

    @NonNull
    @Override
    public String toString() {
        return "user_data{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", total_amount=" + total_worth +
                '}';
    }


}
