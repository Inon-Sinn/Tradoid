package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.example.tradoid.firebase.model.FirebaseDBUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Sign_In extends AppCompatActivity {

    public DatabaseReference ref = new FirebaseDBUser().getRef();

    public String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.sign_in_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(login.class));

        // Connection to Stock Market TODO only temporary remove later
        Button to_Stock_Market_btn = findViewById(R.id.sign_in_To_stock_market);
        to_Stock_Market_btn.setOnClickListener(v -> sendToActivity(Stock_Market.class));

        // Connection to User List TODO only temporary remove later
        Button to_User_List_btn = findViewById(R.id.sign_in_To_user_list);
        to_User_List_btn.setOnClickListener(v -> sendToActivity(User_List.class));

        // Connection to Ban Msg TODO only temporary remove later
        Button to_Ban_msg_btn = findViewById(R.id.sign_in_To_ban_msg);
        to_Ban_msg_btn.setOnClickListener(v -> sendToActivity(Ban_msg.class));

        // Making Text View "Sign Up" Clickable
        TextView tv_sign_up = findViewById(R.id.tv_from_sign_in_to_sign_up);
        tv_sign_up.setOnClickListener(v -> sendToActivity(Sign_Up.class));

        // Making the Text View "Forgot your password?" Clickable
        TextView tv_forgot_pass = findViewById(R.id.tv_forgot_pass_sign_in);
        tv_forgot_pass.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"Not Implemented",Toast.LENGTH_SHORT).show());

        // TextInputLayouts
        TextInputLayout email_layout = findViewById(R.id.textInputLayout_email_sign_in);
        TextInputLayout password_layout = findViewById(R.id.textInputLayout_password_sign_in);

        //All TextInput Editors
        TextInputEditText et_email = findViewById(R.id.edit_text_sign_in_email);
        TextInputEditText et_password = findViewById(R.id.edit_text_sign_in_password);

        // Error Text Watchers for invalid input
        et_email.addTextChangedListener(new emailTextWatcher(email_layout));
        et_password.addTextChangedListener(new passwordTextWatcher(password_layout));

        // Connecting to Error Msg Text view
        TextView errortv = findViewById(R.id.tv_error_msg_sign_in);

        //Connecting the Sign in Button
        Button btn_sign_up = findViewById(R.id.btn_sign_in);
        btn_sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Check if all fields are filled
                        if(et_email.getText().length() == 0)
                            email_layout.setError("Field Required");
                        if(et_password.getText().length() == 0)
                            password_layout.setError("Field Required");

                        // Check if all fields have no error TODO change to setting a tv
                        if(email_layout.getError()!=null ||password_layout.getError()!=null){
                            Toast.makeText(getApplicationContext(),"Invalid",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"No Error",Toast.LENGTH_SHORT).show();
                            email = et_email.getText().toString();
                            password = et_password.getText().toString();

                            String userType = "";
                            for (DataSnapshot user: snapshot.child("users").getChildren()){
                                if (Objects.requireNonNull(user.child("email").getValue()).toString().equals(email) &&
                                Objects.requireNonNull(user.child("password").getValue()).toString().equals(password)){
                                    userType = "user";
                                }
                            }
                            for (DataSnapshot admin: snapshot.child("admins").getChildren()){
                                if (Objects.requireNonNull(admin.child("email").getValue()).toString().equals(email) &&
                                        Objects.requireNonNull(admin.child("password").getValue()).toString().equals(password)) {
                                    userType = "admin";
                                }
                            }

                            if (userType.equals("user")){
                                //TODO user log in
                                System.out.println("FOUND USER");
                                sendToActivity(Stock_Market.class);
                            }
                            else if (userType.equals("admin")){
                                //TODO admin log in
                                System.out.println("FOUND ADMIN");
                                sendToActivity(User_List.class);
                            }
                            else{
                                //TODO user not found
                                System.out.println("DIDN'T FIND");
                                errortv.setText("Incorrect email or password");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // keep empty for now
                    }
                });
            }
        });
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID","123");
        startActivity(intent);
    }
}