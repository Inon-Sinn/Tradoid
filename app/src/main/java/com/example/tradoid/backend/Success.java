package com.example.tradoid.backend;

public class Success {
    private final boolean success;

    public Success(boolean success){
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
