package com.example.tradoid.firebase.model;

import static com.example.tradoid.Data_handling.MD5.getMd5;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpViewModel extends ViewModel implements FirestoreBaseModel{

    public enum Availability{
        AVAILABLE,
        EMAIL_TAKEN,
        USERNAME_TAKEN
    }

    private MutableLiveData<Integer> userStatus = new MutableLiveData<>();
    private MutableLiveData<Integer> adminStatus = new MutableLiveData<>();

    private MutableLiveData<String> userId = new MutableLiveData<>();

    public void reset(){
        userStatus = new MutableLiveData<>();
        adminStatus = new MutableLiveData<>();
        userId = new MutableLiveData<>();
    }

    public void signUpTryUsers(String username, String email){
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    boolean changed = false;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        if (Objects.equals(document.get("email"), email)){
                            userStatus.setValue(Availability.EMAIL_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                        if (Objects.equals(document.get("username"), username)){
                            userStatus.setValue(Availability.USERNAME_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        userStatus.setValue(Availability.AVAILABLE.ordinal());
                    }
                }
            }
        });
    }

    public void signUpTryAdmins(String username, String email){
        db.collection("admins").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    boolean changed = false;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        if (Objects.equals(document.get("email"), email)){
                            adminStatus.setValue(Availability.EMAIL_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                        if (Objects.equals(document.get("username"), username)){
                            adminStatus.setValue(Availability.USERNAME_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        adminStatus.setValue(Availability.AVAILABLE.ordinal());
                    }
                }
            }
        });
    }

    public void addNewUser(String username, String email, String password){
        String md5 = getMd5(password);
        Map<String, Object> newUser = new HashMap<String, Object>(){{
            put("username", username);
            put("email", email);
            put("password", md5);

            // default values
            put("balance", 0.0);
            put("banned", false);
        }};
        db.collection("users").add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                userId.setValue(documentReference.getId());
            }
        });
    }

    public LiveData<Integer> getUserStatus(){
        return userStatus;
    }

    public LiveData<Integer> getAdminStatus(){
        return adminStatus;
    }

    public LiveData<String> getUserId(){
        return userId;
    }
}
