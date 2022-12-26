package com.example.tradoid.firebase.model;

import android.annotation.SuppressLint;

import com.google.firebase.firestore.FirebaseFirestore;

public interface FirestoreBaseModel {

    @SuppressLint("StaticFieldLeak")
    FirebaseFirestore db = FirebaseFirestore.getInstance();
}
