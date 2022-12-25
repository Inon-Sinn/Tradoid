package com.example.tradoid.firebase.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreBaseModel {

    protected FirebaseFirestore ref;

    public FirestoreBaseModel(){
        ref = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getRef(){
        return ref;
    }
}
