package com.example.tradoid.backend;

public class LogInTry {
    private final String type;
    private final String userId;

    public LogInTry(String type, String userId){
        this.type = type;
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }
}
