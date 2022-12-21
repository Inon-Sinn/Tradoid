package com.example.tradoid.Data_handling;

public class user_data {

    String name;
    String email;
    double total_amount;

    public user_data(String name, String email, double total_amount) {
        this.name = name;
        this.email = email;
        this.total_amount = total_amount;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    @Override
    public String toString() {
        return "user_data{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", total_amount=" + total_amount +
                '}';
    }
}
