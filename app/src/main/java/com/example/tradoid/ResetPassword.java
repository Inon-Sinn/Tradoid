package com.example.tradoid;

import static com.example.tradoid.backend.MD5.getMd5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.*;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    Gson gson = new Gson();

    String email;

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        if (getIntent().hasExtra("email")){email = getIntent().getStringExtra("email");}

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.res_pas_back_arrow);
        String back_arrow_text  = "Sign In";
        tv_back_arrow.setText(back_arrow_text);
        tv_back_arrow.setOnClickListener(v -> startActivity( new Intent(this,Sign_In.class)));

        // TextInputLayouts
        TextInputLayout password_layout = findViewById(R.id.textInputLayout_password_res_password);
        TextInputLayout confirm_layout = findViewById(R.id.textInputLayout_confirm_res_password);

        //All TextInput Editors
        TextInputEditText et_password = findViewById(R.id.edit_text_res_pass_password);
        TextInputEditText et_confirm = findViewById(R.id.edit_text_res_password_confirm);

        // Connecting to Error Msg Text view
        TextView error_tv = findViewById(R.id.tv_error_msg_res_password);

        // Error Text Watchers for invalid input
        et_password.addTextChangedListener(new passwordTextWatcher(password_layout));
        et_confirm.addTextChangedListener(new passwordTextWatcher(confirm_layout));

        //Connecting the Update Button TODO
        Button btn_sign_up = findViewById(R.id.btn_res_password);
        btn_sign_up.setOnClickListener(v -> {
            // Check if all fields are filled
            if(Objects.requireNonNull(et_password.getText()).length() == 0)
                password_layout.setError("Field Required");
            if(Objects.requireNonNull(et_confirm.getText()).length() == 0)
                confirm_layout.setError("Field Required");

            if (password_layout.getError() == null && confirm_layout.getError() == null){
                String password = et_password.getText().toString();

                Map<String, Object> payload = new HashMap<>();
                payload.put("email", email);
                payload.put("newPassword", getMd5(password));

                Response response = client.sendPost("reset_password", payload);
                if (response.passed()){
                    Success success = gson.fromJson(response.getData(), Success.class);
                    if (success.isSuccess()){
                        Toast.makeText(getApplicationContext(),"Password was reset successfully",Toast.LENGTH_SHORT).show();
                        sendToActivity(Sign_In.class);
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