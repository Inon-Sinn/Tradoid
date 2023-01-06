package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ResetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Get user email
        String email ="";
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
        });



    }

    // Sends to other screens
    public void sendToActivity(Class cls, String userId){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID", userId);
        startActivity(intent);
    }
}