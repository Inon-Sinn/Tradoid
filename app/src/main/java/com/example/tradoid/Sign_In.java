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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Sign_In extends AppCompatActivity {

    public String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Creating a custom Toolbar
        Toolbar sign_in_toolbar = findViewById(R.id.toolbar_sign_in);
        setSupportActionBar(sign_in_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.sign_in_To_login);
        back_arrow.setOnClickListener(v -> sendToActivity(login.class));

        // Connection to Stock Market TODO only temporary remove later
        Button to_Stock_Market_btn = findViewById(R.id.sign_in_To_stock_market);
        to_Stock_Market_btn.setOnClickListener(v -> sendToActivity(Stock_Market.class));

        // Connection to User List TODO only temporary remove later
        Button to_User_List_btn = findViewById(R.id.sign_in_To_user_list);
        to_User_List_btn.setOnClickListener(v -> sendToActivity(User_List.class));

        // Connecting to the Text View
        TextView tv_sign_in = findViewById(R.id.tv_from_sign_in_to_sign_up);
        TextView tv_forgot_pass = findViewById(R.id.tv_forgot_pass_sign_in);

        // Making the Text View "Sign In" Clickable
        String sing_in_text = "Sign Up";
        SpannableString ss_sign_in = new SpannableString(sing_in_text);
        ClickableSpan cs_sign_in = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                sendToActivity(Sign_Up.class);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        BackgroundColorSpan bcs = new BackgroundColorSpan(Color.WHITE);
        ss_sign_in.setSpan(cs_sign_in,0,sing_in_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_sign_in.setSpan(bcs,0,sing_in_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bacground stays white when clicked
        tv_sign_in.setText(ss_sign_in);
        tv_sign_in.setMovementMethod(LinkMovementMethod.getInstance());

        // Making the Text View "Forgotten Password?" Clickable
        String forgot_text = "Forgot Password?";
        SpannableString ss_forgot_pass = new SpannableString(forgot_text);
        ClickableSpan cs_forgot_pass = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(getApplicationContext(),"Not Implemented",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        ss_forgot_pass.setSpan(cs_forgot_pass,0,forgot_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss_forgot_pass.setSpan(bcs,0,forgot_text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bacground stays white when clicked
        tv_forgot_pass.setText(ss_forgot_pass);
        tv_forgot_pass.setMovementMethod(LinkMovementMethod.getInstance());

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
                    //check that passwords are equal
                    email = et_email.getText().toString();
                    password = et_password.getText().toString();
                    int signIn = sign_into_Account();
                    System.out.println(signIn);
                    if(signIn == 0){
                        errortv.setText("Incorrect email or password");
                    }
                    if(signIn != 0){
                        errortv.setText("");
                    }
                }
            }
        });
    }

    public int  sign_into_Account(){
        return 0;
        // TODO should probably return an int that tells if the password is incorrect
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}