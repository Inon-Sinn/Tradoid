package com.example.tradoid.firebase.model;

import static com.example.tradoid.Data_handling.MD5.getMd5;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class SignInViewModel extends ViewModel implements FirestoreBaseModel{

    private MutableLiveData<Boolean> isUser = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAdmin = new MutableLiveData<>();

    private MutableLiveData<String> userId = new MutableLiveData<>();

    private MutableLiveData<Boolean> isBanned = new MutableLiveData<>();

    public void reset(){
        isUser = new MutableLiveData<>();
        isAdmin = new MutableLiveData<>();
        userId = new MutableLiveData<>();
        isBanned = new MutableLiveData<>();
    }

    public void signInTryUsers(String email, String password){
        String md5 = getMd5(password);
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    boolean success = false;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        if (Objects.equals(document.get("email"), email) &&
                                Objects.equals(document.get("password"), md5)) {
                            isUser.setValue(true);
                            userId.setValue(document.getId());
                            success = true;

                            if (Objects.equals(document.get("banned"), true)){
                                isBanned.setValue(true);
                            } else {
                                isBanned.setValue(false);
                            }

                            break;
                        }
                    }
                    if (!success){
                        isUser.setValue(false);
                    }
                }
            }
        });
    }

    public void singInTryAdmins(String email, String password){
        String md5 = getMd5(password);
        db.collection("admins").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean success = false;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (Objects.equals(document.get("email"), email) &&
                                Objects.equals(document.get("password"), md5)) {
                            isAdmin.setValue(true);
                            userId.setValue(document.getId());
                            success = true;
                            break;
                        }
                    }
                    if (!success){
                        isAdmin.setValue(false);
                    }
                }
            }
        });
    }

    public LiveData<Boolean> getIsUser(){
        return isUser;
    }

    public LiveData<Boolean> getIsAdmin(){
        return isAdmin;
    }

    public LiveData<String> getUserId(){
        return userId;
    }

    public LiveData<Boolean> getIsBanned(){
        return isBanned;
    }
}
