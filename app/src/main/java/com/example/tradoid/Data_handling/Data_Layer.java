package com.example.tradoid.Data_handling;


import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.example.tradoid.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;



// This class implements all calls to the Database
public class Data_Layer {

    // Stock Market data
    public List<example_Item> example_list;

    // Firebase
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public Data_Layer(){}

    public Data_Layer(String fragment) {
        example_list = new ArrayList<>();
        data_by_frag(fragment);
    }

    public List<example_Item> get_Stocks_data(){
        return example_list;
    }

    public List<example_Item> get_User_data(){
        return example_list;
    }

    // changes the data by the fragment we are on
    public void data_by_frag(String frag){
        frag = frag.toLowerCase(Locale.ROOT);
        if(frag.equals("stock"))
            get_Stocks();
        if (frag.equals("watchlist"))
            get_Watchlist();
        if (frag.equals("portfolio"))
            get_Portfolio();
        if (frag.equals("users"))
            get_Users();
        if (frag.equals("banned"))
            get_Banned();
    }

    public void get_Stocks(){
        String s1[] = {"Samuel Sinn","Revital","Liel","Tehila","Inon","Ofir","Gilad","Noa","Yair","Mapel"};
        String s2[] = {"1","2","3","4","5","6","7","8","9","10"};
        int images[] = {R.drawable.ic_arrow_back, R.drawable.ic_ban, R.drawable.ic_bookmark,
                R.drawable.ic_dashboard, R.drawable.ic_identity, R.drawable.ic_kebab_menu,
                R.drawable.ic_menu, R.drawable.ic_portfolio, R.drawable.ic_list, R.drawable.ic_logout};
        for (int i = 0; i < s1.length; i++) {
            example_list.add(new example_Item(s1[i],s2[i],images[i]));
        }
    }

    public void get_Watchlist(){
        String s1[] = {"Samuel Sinn","Revital","Liel","Tehila","Inon"};
        String s2[] = {"1","2","3","4","5"};
        int images[] = {R.drawable.ic_arrow_back, R.drawable.ic_ban, R.drawable.ic_bookmark,
                R.drawable.ic_dashboard, R.drawable.ic_identity};
        for (int i = 0; i < s1.length; i++) {
            example_list.add(new example_Item(s1[i],s2[i],images[i]));
        }
    }

    public void get_Portfolio(){
        String s1[] = {"Ofir","Gilad","Noa","Yair","Mapel"};
        String s2[] = {"6","7","8","9","10"};
        int images[] = {R.drawable.ic_kebab_menu,R.drawable.ic_menu, R.drawable.ic_portfolio,
                R.drawable.ic_list, R.drawable.ic_logout};
        for (int i = 0; i < s1.length; i++) {
            example_list.add(new example_Item(s1[i],s2[i],images[i]));
        }
    }

    public void get_Users(){
        get_Stocks();
    }

    public void get_Banned(){
        get_Watchlist();
    }

    public void sign_up(String username, String email, String password, String password_confirm){
        Map<String, Object> users = new HashMap<String, Object>(){{
            put("username", username);
            put("email", email);
            put("password", password);
        }};
        firestore.collection("users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                System.out.println("Success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Failure");
            }
        });
    }



    /*
    We will make that instead of example item we will have 2 classes stock and user
    Data layer will have all stocks and users
     */
}