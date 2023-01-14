package com.example.tradoid.backend;

public class Amounts {
    private final int totalUsers;
    private final double totalRevenue;
    private final int unbannedAmount;
    private final int bannedAmount;
    private final int adminAmount;

    public Amounts(int totalUsers, double totalRevenue, int unbannedAmount, int bannedAmount, int adminAmount){
        this.totalUsers = totalUsers;
        this.totalRevenue = totalRevenue;
        this.unbannedAmount = unbannedAmount;
        this.bannedAmount = bannedAmount;
        this.adminAmount = adminAmount;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public float getTotalRevenue() {
        return (float) totalRevenue;
    }

    public int getUnbannedAmount() {
        return unbannedAmount;
    }

    public int getBannedAmount() {
        return bannedAmount;
    }

    public int getAdminAmount() {
        return adminAmount;
    }
}
