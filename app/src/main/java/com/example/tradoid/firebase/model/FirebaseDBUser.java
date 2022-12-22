package com.example.tradoid.firebase.model;

import androidx.annotation.NonNull;

import com.example.tradoid.firebase.model.objects.UserObj;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBUser extends FirebaseBaseModel{

    public void addUserToDB(String username, String email, String password){
        UserObj userObj = new UserObj(username, email, password);
        myRef.child("users").child(username).setValue(userObj);
    }

    public boolean checkIfUsernameExists(String username){
        List<Boolean> tokenContainer = new ArrayList<>();
        myRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    if (username.equals(snapshot.getKey())){
                        tokenContainer.add(true);
                        return;
                    }
                }
                tokenContainer.add(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return tokenContainer.get(0);
    }

    public DatabaseReference getRef(){return myRef;}
}
