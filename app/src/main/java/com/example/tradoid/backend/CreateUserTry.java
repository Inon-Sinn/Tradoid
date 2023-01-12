package com.example.tradoid.backend;

public class CreateUserTry {
    private final int availabilityStatus;
    private final String userId;

    public CreateUserTry(int availabilityStatus, String userId){
        this.availabilityStatus = availabilityStatus;
        this.userId = userId;
    }

    public int getAvailabilityStatus() {
        return availabilityStatus;
    }

    public String getUserId() {
        return userId;
    }
}
