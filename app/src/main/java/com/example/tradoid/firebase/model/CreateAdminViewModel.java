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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAdminViewModel extends ViewModel implements FirestoreBaseModel{

    public enum Availability{
        AVAILABLE,
        EMAIL_TAKEN,
        USERNAME_TAKEN
    }

    private MutableLiveData<Integer> userStatus = new MutableLiveData<>();
    private MutableLiveData<Integer> adminStatus = new MutableLiveData<>();

    private MutableLiveData<String> adminId = new MutableLiveData<>();

    public void reset(){
        userStatus = new MutableLiveData<>();
        adminStatus = new MutableLiveData<>();
        adminId = new MutableLiveData<>();
    }

    public void createTryUsers(String username, String email){
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    boolean changed = false;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        if (Objects.equals(document.get("email"), email)){
                            userStatus.setValue(SignUpViewModel.Availability.EMAIL_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                        if (Objects.equals(document.get("username"), username)){
                            userStatus.setValue(SignUpViewModel.Availability.USERNAME_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        userStatus.setValue(SignUpViewModel.Availability.AVAILABLE.ordinal());
                    }
                }
            }
        });
    }

    public void createTryAdmins(String username, String email){
        db.collection("admins").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    boolean changed = false;
                    for (QueryDocumentSnapshot document: task.getResult()){
                        if (Objects.equals(document.get("email"), email)){
                            adminStatus.setValue(SignUpViewModel.Availability.EMAIL_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                        if (Objects.equals(document.get("username"), username)){
                            adminStatus.setValue(SignUpViewModel.Availability.USERNAME_TAKEN.ordinal());
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        adminStatus.setValue(SignUpViewModel.Availability.AVAILABLE.ordinal());
                    }
                }
            }
        });
    }

    public void createNewAdmin(String username, String email, String password){
        String md5 = getMd5(password);
        Map<String, Object> newAdmin = new HashMap<String, Object>(){{
            put("username", username);
            put("email", email);
            put("password", md5);
        }};
        db.collection("admins").add(newAdmin).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                adminId.setValue(documentReference.getId());
            }
        });
    }

    public LiveData<Integer> getUserStatus(){
        return userStatus;
    }

    public LiveData<Integer> getAdminStatus(){
        return adminStatus;
    }

    public LiveData<String> getAdminId(){
        return adminId;
    }
}