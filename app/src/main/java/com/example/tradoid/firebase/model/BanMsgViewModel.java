package com.example.tradoid.firebase.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

public class BanMsgViewModel extends ViewModel implements FirestoreBaseModel {

    public MutableLiveData<String> banMsg = new MutableLiveData<>();

    public void reset(){
        banMsg = new MutableLiveData<>();
    }

    public void loadBanMsg(String userId){
        db.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                banMsg.setValue(Objects.requireNonNull(documentSnapshot.get("ban_msg")).toString());
            }
        });
    }

    public LiveData<String> getBanMsg(){
        return banMsg;
    }
}
