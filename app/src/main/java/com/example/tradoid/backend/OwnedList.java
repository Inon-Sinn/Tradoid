package com.example.tradoid.backend;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class OwnedList {
    private final List<Owned> ownedList;

    public OwnedList(Owned[] ownedList){
        this.ownedList = Arrays.asList(ownedList);
    }

    public List<Owned> getOwnedList() {
        return ownedList;
    }

    public double getTotal(){
        double total = 0;
        for (Owned owned : this.ownedList){
            total += owned.getAmount() * owned.getStock().getCurrentPrice();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(total));
    }
}
