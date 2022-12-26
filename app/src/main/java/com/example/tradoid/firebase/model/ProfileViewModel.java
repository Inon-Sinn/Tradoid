package com.example.tradoid.firebase.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileViewModel extends ViewModel implements FirestoreBaseModel{

    private MutableLiveData<String> username = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();

    public void reset(){
        username = new MutableLiveData<>();
        email = new MutableLiveData<>();
    }

    public void loadProfileUsers(String userId){
        db.collection("users").document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                username.setValue(Objects.requireNonNull(documentSnapshot.get("username")).toString());
                email.setValue(Objects.requireNonNull(documentSnapshot.get("email")).toString());
            }
        });
    }

    public LiveData<String> getUsername(){
        return username;
    }

    public LiveData<String> getEmail(){
        return email;
    }
}
