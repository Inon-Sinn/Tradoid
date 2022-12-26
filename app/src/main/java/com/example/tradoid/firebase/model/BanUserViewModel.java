package com.example.tradoid.firebase.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

public class BanUserViewModel extends ViewModel implements FirestoreBaseModel{

    private MutableLiveData<Boolean> finishedBan = new MutableLiveData<>();
    private MutableLiveData<Boolean> finishedUnban = new MutableLiveData<>();

    public void reset(){
        finishedBan = new MutableLiveData<>();
        finishedUnban = new MutableLiveData<>();
    }

    public void banUser(String userId, String reason){
        db.collection("users").document(userId).update(new HashMap<String, Object>(){{
            put("banned", true);
            put("ban_msg", reason);
        }}).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                finishedBan.setValue(true);
            }
        });
    }

    public void unbanUser(String userId){
        db.collection("users").document(userId).update(new HashMap<String, Object>(){{
            put("banned", false);
            put("ban_msg", "");
        }}).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                finishedUnban.setValue(true);
            }
        });
    }

    public LiveData<Boolean> getFinishedBan(){
        return finishedBan;
    }

    public LiveData<Boolean> getFinishedUnban(){
        return finishedUnban;
    }
}
