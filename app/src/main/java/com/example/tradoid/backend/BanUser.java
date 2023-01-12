package com.example.tradoid.backend;

public class BanUser {
    private final boolean success;

    public BanUser(boolean success){
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
