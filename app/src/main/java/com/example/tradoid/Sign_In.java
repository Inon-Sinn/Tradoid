package com.example.tradoid;

import static com.example.tradoid.backend.MD5.getMd5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tradoid.Business_Logic.GMailSender;
import com.example.tradoid.Business_Logic.Utils;
import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.example.tradoid.backend.*;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class Sign_In extends AppCompatActivity {

    Gson gson = new Gson();

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.sign_in_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(login.class));

        // Making Text View "Sign Up" Clickable
        TextView tv_sign_up = findViewById(R.id.tv_from_sign_in_to_sign_up);
        tv_sign_up.setOnClickListener(v -> sendToActivity(Sign_Up.class));

        // TextInputLayouts
        TextInputLayout email_layout = findViewById(R.id.textInputLayout_email_sign_in);
        TextInputLayout password_layout = findViewById(R.id.textInputLayout_password_sign_in);

        //All TextInput Editors
        TextInputEditText et_email = findViewById(R.id.edit_text_sign_in_email);
        TextInputEditText et_password = findViewById(R.id.edit_text_sign_in_password);

        // Connecting to Error Msg Text view
        TextView error_tv = findViewById(R.id.tv_error_msg_sign_in);

        // Making the Text View "Forgot your password?" Clickable
        TextView tv_forgot_pass = findViewById(R.id.tv_forgot_pass_sign_in);
        tv_forgot_pass.setOnClickListener(v -> sendMail(String.valueOf(et_email.getText()), error_tv));

        // Error Text Watchers for invalid input
        et_email.addTextChangedListener(new emailTextWatcher(email_layout));
        et_password.addTextChangedListener(new passwordTextWatcher(password_layout));

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
                if(email_layout.getError() == null && password_layout.getError() == null){
                    String email, password;
                    email = et_email.getText().toString();
                    password = et_password.getText().toString();

                    // preparing data to send
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("email", email);
                    userData.put("password", getMd5(password));

                    // checking if the user exists
                    Response response = client.sendPost("log_in", userData);
                    // checking for errors
                    if (response.passed()){
                        // getting log in data
                        LogInTry logInTry = gson.fromJson(response.getData(), LogInTry.class);
                        // if it is a user log in
                        if (logInTry.getType() == null){
                            // did not find user/admin
                            error_tv.setText("wrong email or password!");
                        }
                        else if (logInTry.getType().equals("user")){
                            String userId = logInTry.getUserId();
                            // checking if the user is banned
                            Response isBannedResponse = client.sendGet("is_banned/" + userId);
                            // checking for errors
                            if (response.passed()){
                                // getting banned user data
                                IsBanned isBanned = gson.fromJson(isBannedResponse.getData(), IsBanned.class);
                                // if the user is banned
                                if (isBanned.getIsBanned()){
                                    // sending to ban page

                                    Map<String, String> params = new HashMap<>();
                                    params.put("reason", isBanned.getReason());

                                    sendToActivity(Ban_msg.class, params);
                                } else {
                                    // getting user data
                                    Response userResponse = client.sendGet("get_user/" + userId);
                                    if (userResponse.passed()){
                                        // sending the main user page

                                        Map<String, Object> payload = new HashMap<>();
                                        payload.put("userId", userId);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
                                            try {
                                                payload.put("lastSeen", dateFormat.parse(ZonedDateTime.now().toString()));
                                            } catch (ParseException e) {
                                                payload.put("lastSeen", new Date());
                                            }
                                        } else {
                                            payload.put("lastSeen", new Date());
                                        }

                                        client.sendPost("update_last_seen", payload);

                                        Map<String, String> params = new HashMap<>();
                                        params.put("user", userResponse.getData());

                                        sendToActivity(Stock_Market.class, params);
                                    }
                                }
                            } else {
                                // request did not pass
                                error_tv.setText("server error. please try again later.");
                            }
                            // if it is an admin
                        } else if (logInTry.getType().equals("admin")){
                            String adminId = logInTry.getUserId();
                            // sending to main admin page

                            Map<String, String> params = new HashMap<>();
                            params.put("adminId", adminId);

                            sendToActivity(User_List.class, params);
                        }
                    } else {
                        // request did not pass
                        error_tv.setText("server error. please try again later.");
                    }
                }
            }
        });
    }

    //send the email, second way
    private void sendMail(String email, TextView errorTv) {

        // check if the mail is valid
        if(!validMail(email)){
            String error = "Email does not belong to any user";
            errorTv.setText(error);
            return;
        }

        // The user Exists
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        // Sending the Mail
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Random Validation number
                    Random random = new Random();
                    int val_code = random.nextInt(1000000);

                    // Adding missing zero prefixes of the validation code
                    StringBuilder code = new StringBuilder(String.valueOf(val_code));
                    while( code.length() < 6){
                        code.insert(0, "0");
                    }

                    // Send the email
                    GMailSender sender = new GMailSender(Utils.EMAIL, Utils.PASSWORD);
                    sender.sendMail("Password Reset",
                            "Validation code is " + code,
                            "tradoidapp@gmail.com",
                            email); // Who gets the email
                    dialog.dismiss();

                    // Send the user to the Email Validation screen with the needed data
                    Intent toEmailVal = new Intent(getApplicationContext(), EmailValidation.class);
                    toEmailVal.putExtra("email",email);
                    toEmailVal.putExtra("val_code",val_code);
                    startActivity(toEmailVal);
                } catch (Exception e) {
                    System.out.println("DID NOT WORK");
                }
            }
        });
        sender.start();
    }

    // checks if the users email is valid if the user forgot his password
    public boolean validMail(String email){
        Response response = client.sendGet("email_exists/" + email);
        if (response.passed()){
            Success success = gson.fromJson(response.getData(), Success.class);
            return success.isSuccess();
        }
        return false;
    }

    // Sends to other screens
    public void sendToActivity(Class cls, Map<String, String> params){
        Intent intent = new Intent(this, cls);
        for (Map.Entry<String, String> param: params.entrySet()){
            intent.putExtra(param.getKey(), param.getValue());
        }
        startActivity(intent);
    }
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}