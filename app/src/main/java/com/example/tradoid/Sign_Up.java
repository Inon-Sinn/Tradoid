package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static com.example.tradoid.backend.MD5.getMd5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.example.tradoid.Business_Logic.usernameTextWatcher;
import com.example.tradoid.backend.*;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.sign_up_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(login.class));

        // Making Text View "Sign Up" Clickable
        TextView tv_sign_in = findViewById(R.id.tv_from_sign_up_to_sign_in);
        tv_sign_in.setOnClickListener(v -> sendToActivity(Sign_In.class));

        // TextInputLayouts
        TextInputLayout name_layout = findViewById(R.id.textInputLayout_name_sign_up);
        TextInputLayout email_layout = findViewById(R.id.textInputLayout_email_sign_up);
        TextInputLayout password_layout = findViewById(R.id.textInputLayout_password_sign_up);
        TextInputLayout confirm_layout = findViewById(R.id.textInputLayout_confirm_sign_up);


        //All TextInput Editors
        TextInputEditText et_name = findViewById(R.id.reason_tv);
        TextInputEditText et_email = findViewById(R.id.edit_text_sign_up_email);
        TextInputEditText et_password = findViewById(R.id.edit_text_sign_up_password);
        TextInputEditText et_confirm = findViewById(R.id.edit_text_sign_up_confirm);

        // Error Text Watchers for invalid input
        et_name.addTextChangedListener(new usernameTextWatcher(name_layout));
        et_email.addTextChangedListener(new emailTextWatcher(email_layout));
        et_password.addTextChangedListener(new passwordTextWatcher(password_layout));
        et_confirm.addTextChangedListener(new passwordTextWatcher(confirm_layout));

        //Connecting the Sign up Button
        Button btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all fields are filled
                if(et_name.getText().length() == 0)
                    name_layout.setError("Field Required");
                if(et_email.getText().length() == 0)
                    email_layout.setError("Field Required");
                if(et_password.getText().length() == 0)
                    password_layout.setError("Field Required");
                if(et_confirm.getText().length() == 0)
                    confirm_layout.setError("Field Required");

                // Check if all fields have no error TODO change to setting a tv
                if(name_layout.getError() == null && email_layout.getError() == null &&
                        password_layout.getError() == null && confirm_layout.getError() == null ){
                    //check that passwords are equal
                    if (et_password.getText().toString().equals(et_confirm.getText().toString())) {
                        String username, email, password;
                        username = et_name.getText().toString();
                        email = et_email.getText().toString();
                        password = et_password.getText().toString();

                        // checking if username and email are available
                        Map<String, Object> payload = new HashMap<>();
                        payload.put("username", username);
                        payload.put("email", email);
                        payload.put("password", getMd5(password));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
                            try {
                                payload.put("dateCreated", dateFormat.parse(ZonedDateTime.now().toString()));
                            } catch (ParseException e) {
                                payload.put("dateCreated", new Date());
                            }
                        } else {
                            payload.put("dateCreated", new Date());
                        }

                        Response response = client.sendPost("create_user", payload);
                        if (response.passed()) {
                            CreateUserTry createUserTry = new Gson().fromJson(response.getData(), CreateUserTry.class);
                            if (createUserTry.getAvailabilityStatus() == 1) {
                                email_layout.setError("email already in user!");
                            } else if (createUserTry.getAvailabilityStatus() == 2) {
                                name_layout.setError("username already taken!");
                            } else {
                                String userId = createUserTry.getUserId();
                                Response getUserResponse = client.sendGet("get_user/" + userId);
                                if (getUserResponse.passed()){

                                    Map<String, String> params = new HashMap<>();
                                    params.put("user", getUserResponse.getData());

                                    sendToActivity(Stock_Market.class, params);
                                }
                            }
                        }
                    }
                    else{
                        confirm_layout.setError("Passwords must be equal"); //TODO change
                    }
                }
            }
        });
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

