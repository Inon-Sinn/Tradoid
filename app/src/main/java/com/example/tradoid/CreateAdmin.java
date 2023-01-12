package com.example.tradoid;

import static com.example.tradoid.backend.MD5.getMd5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.example.tradoid.Business_Logic.usernameTextWatcher;
import com.example.tradoid.backend.CreateUserTry;
import com.example.tradoid.backend.HttpUtils;
import com.example.tradoid.backend.Response;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class CreateAdmin extends AppCompatActivity {

    String adminId;

    public HttpUtils client = new HttpUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (getIntent().hasExtra("adminId")) {
            adminId = getIntent().getStringExtra("adminId");
        }

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

                        Response response = client.sendPost("create_admin", payload);
                        if (response.passed()) {
                            CreateUserTry createAdminTry = new Gson().fromJson(response.getData(), CreateUserTry.class);
                            if (createAdminTry.getAvailabilityStatus() == 1) {
                                email_layout.setError("email already in user!");
                            } else if (createAdminTry.getAvailabilityStatus() == 2) {
                                name_layout.setError("username already taken!");
                            } else {

                                Map<String, String> params = new HashMap<>();
                                params.put("adminId", adminId);

                                sendToActivity(admin_options.class, params);
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