package com.example.tradoid.Data_handling;

public class example_Item {

    String data1;
    String data2;
    int image;

    public example_Item(String data1, String data2, int image) {
        this.data1 = data1;
        this.data2 = data2;
        this.image = image;
    }

    public String getData1() {
        return data1;
    }

    public String getData2() {
        return data2;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "example_Item{" +
                "data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                '}';
    }
}
