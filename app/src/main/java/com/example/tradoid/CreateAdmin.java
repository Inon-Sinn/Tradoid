package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.example.tradoid.Business_Logic.usernameTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class CreateAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Changing the Title
        TextView title = findViewById(R.id.tv_card_title_sign_up);
        String card_Title = "Create Admin";
        title.setText(card_Title);

        // hide textViews
        TextView haveAnAccount = findViewById(R.id.tv_haveAcount);
        TextView sign_in = findViewById(R.id.tv_from_sign_up_to_sign_in);
        sign_in.setText("");
        haveAnAccount.setVisibility(View.GONE);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.sign_up_back_arrow);
        tv_back_arrow.setOnClickListener(v -> startActivity(new Intent(this,admin_options.class)));
        String backArrowDest = "Options";
        tv_back_arrow.setText(backArrowDest);

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

        //Connecting the Create Account Button
        Button btn_sign_up = findViewById(R.id.btn_sign_up);
        String btn_text = "Create Account";
        btn_sign_up.setText(btn_text);
        btn_sign_up.setOnClickListener(v -> {});

    }
}