package com.example.tradoid.firebase.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class BalanceViewModel extends ViewModel {

    FirebaseFirestore db = new FirestoreBaseModel().getRef();

    private MutableLiveData<Float> balance = new MutableLiveData<>();

    public void reset(){
        balance = new MutableLiveData<>();
    }

    public void loadBalance(String userId){
        db.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                balance.setValue(Float.parseFloat(Objects.requireNonNull(documentSnapshot.get("balance")).toString()));
            }
        });
    }

    public LiveData<Float> getBalance(){
        return balance;
    }

    public void updateBalance(String userId, Float USD){
        db.collection("users").document(userId).update(new HashMap<String, Object>(){{
            put("balance", USD);
        }}).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                balance.setValue(USD);
            }
        });
    }
}
