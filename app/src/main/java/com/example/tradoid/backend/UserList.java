package com.example.tradoid.backend;

import java.util.Arrays;
import java.util.List;

public class UserList {
    private final List<User> userList;

    public UserList(User[] userList){
        this.userList = Arrays.asList(userList);
    }

    public List<User> getUserList() {
        return userList;
    }
}
