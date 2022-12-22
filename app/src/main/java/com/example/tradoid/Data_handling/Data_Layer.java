package com.example.tradoid.Data_handling;


import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


// This class implements all calls to the Database
public class Data_Layer {

    // Stock Market data
    List<stock_data> stocks;

    // User Data
    List<user_data> users;

    public Data_Layer(){
        stocks = new ArrayList<>();
        users = new ArrayList<>();
    }

    // in case the user does not matter
    public Data_Layer(String fragment) {
        stocks = new ArrayList<>();
        users = new ArrayList<>();
        data_for_all(fragment);
    }
    // In case the user does matter
    public Data_Layer(user_data user, String fragment) {
        stocks = new ArrayList<>();
        users = new ArrayList<>();
        data_for_user(user, fragment);
    }

    public List<stock_data> get_Stocks_data(){
        return stocks;
    }

    public List<user_data> get_User_data(){
        return users;
    }

    // changes the data by the fragment we are on
    public void data_for_all(String fragment){
        fragment = fragment.toLowerCase(Locale.ROOT);
        if(fragment.equals("stock"))
            get_Stocks();
        if (fragment.equals("users"))
            get_Users();
        if (fragment.equals("banned"))
            get_Banned();
    }

    public void data_for_user(user_data user, String fragment){
        if (user == null){
            System.out.println("User Can't be null");
        }
        if (fragment.equals("watchlist"))
            get_Watchlist(user);
        if (fragment.equals("portfolio"))
            get_Portfolio(user);
        if (fragment.equals("status page"))
            get_Portfolio(user);
        if (fragment.equals("user status"))
            get_Portfolio(user);

    }

    public void get_Stocks(){
        String[] s1 = {"AAPL","MSFT","AMZN","GOOG","FB","BRK-A","V","JPM","JNJ"};
        String[] s2 = {"Apple, Inc","Microsoft Corp.","Amazon.com, Inc.","Alphabet, Inc.","Facebook, Inc.","Berkshire Hathaway, Inc.","Via, Inc.","JPMorgan Chase & Co.","Johnson & Johnson"};
        double[] price = {148.12, 299.79, 3450, 2868.12, 376.53, 416996.01,223.03,157.07,164.8};
        double[] change = {-1.43,2.80,-7.17,-1.18,0.02,-3804.99,-1.60,-2.79,-1.00};
        for (int i = 0; i < s1.length; i++) {
            stocks.add(new stock_data(s1[i],s2[i],price[i],change[i]));
        }
    }

    public void get_Watchlist(user_data user){
        get_Stocks();
        stocks.subList(0, 5).clear();
    }

    public void get_Portfolio(user_data user){
        get_Stocks();
        stocks.subList(stocks.size()-5, stocks.size()-1).clear();
        //should add the amount in the user_data
        List<double[]> amount = new ArrayList<>();
        Random rand = new Random();
        int usd_bound = 100000,randUsd;
        for (int i = 0; i < stocks.size(); i++) {
            randUsd = rand.nextInt(usd_bound);
            amount.add(new double[]{randUsd,randUsd/stocks.get(i).getTotal_Price()});
        }
        user.setStock_amount(amount);
    }

    public void get_Users(){
        String[] s1 = {"Samuel Sinn","Revital","Liel","Tehila","Inon","Ofir","Gilad","Noa","Yair","Maple"};
        String[] s2 = {"samuel@mail.com","Revital@mail.com","Liel@mail.com","Tehila@mail.com","Inon@mail.com","Ofir@mail.com","Gilad@mail.com","Noa@mail.com","Yair@mail.com","Maple@mail.com"};
        double[] d3 = {1500000,165,15665,56975,948465,48972,1231,-6565456,999999999,.564};
        for (int i = 0; i < s1.length; i++) {
            users.add(new user_data(s1[i],s2[i],d3[i]));
        }
    }

    public void get_Banned(){
        get_Users();
        users.subList(0, 5).clear();
    }

//    public void sign_up(String username, String email, String password, String password_confirm){
//        Map<String, Object> users = new HashMap<String, Object>(){{
//            put("username", username);
//            put("email", email);
//            put("password", password);
//        }};
//        fire_store.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                System.out.println("Success");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                System.out.println("Failure");
//            }
//        });
//    }

    /*
    We will make that instead of example item we will have 2 classes stock and user
    Data layer will have all stocks and users



     */


}