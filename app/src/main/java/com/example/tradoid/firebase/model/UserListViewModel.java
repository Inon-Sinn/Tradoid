package com.example.tradoid.firebase.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tradoid.Data_handling.user_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserListViewModel extends ViewModel implements FirestoreBaseModel{

    private MutableLiveData<List<user_data>> userList = new MutableLiveData<>();
    private MutableLiveData<List<user_data>> bannedList = new MutableLiveData<>();

    public void reset(){
        userList = new MutableLiveData<>();
        bannedList = new MutableLiveData<>();
    }

    public void loadUserList(){
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<user_data> tempList = new ArrayList<>();
                    for (QueryDocumentSnapshot document: task.getResult()){
                        String userId = document.getId();
                        String username = Objects.requireNonNull(document.get("username")).toString();
                        String email = Objects.requireNonNull(document.get("email")).toString();
                        double balance = Double.parseDouble(Objects.requireNonNull(document.get("balance")).toString());
                        user_data newUser = new user_data(username, email, balance, userId);
                        tempList.add(newUser);
                    }
                    userList.setValue(tempList);
                }
            }
        });
    }

    public void loadBannedUserList(){
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<user_data> tempList = new ArrayList<>();
                    for (QueryDocumentSnapshot document: task.getResult()){
                        if (Objects.equals(document.get("banned"), true)){
                            String userId = document.getId();;
                            String username = Objects.requireNonNull(document.get("username")).toString();
                            String email = Objects.requireNonNull(document.get("email")).toString();
                            double balance = Double.parseDouble(Objects.requireNonNull(document.get("balance")).toString());
                            user_data newUser = new user_data(username, email, balance, userId);
                            tempList.add(newUser);
                        }
                    }
                    bannedList.setValue(tempList);
                }
            }
        });
    }

    public LiveData<List<user_data>> getUserList(){
        return userList;
    }

    public LiveData<List<user_data>> getBannedList(){
        return bannedList;
    }
}
