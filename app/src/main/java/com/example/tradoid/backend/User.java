package com.example.tradoid.backend;

public class User {
    private final String userId;
    private final String username;
    private final String email;
    private double balance;

    public User(String userId, String username, String email, double balance){
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
