package com.example.tradoid.Data_handling;

import androidx.annotation.NonNull;

import java.util.List;

public class user_data {

    String name;
    String email;
    double total_worth;
    String userId;
    List<double[]> stock_amount;

    public user_data(String name, String email, double total_amount, String userId) {
        this.name = name;
        this.email = email;
        this.total_worth = total_amount;
        this.userId = userId;
    }

    public String toFilterBy(){
        return getName();
    }

    public String getName() {
        return name;
    }

    public String getID(){
        return userId;
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
