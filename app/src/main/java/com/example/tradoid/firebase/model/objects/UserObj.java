package com.example.tradoid.firebase.model.objects;

import java.util.HashMap;
import java.util.Map;

public class UserObj {
    public String username;
    public String email;
    public String password;

    public UserObj(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
