package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tradoid.Business_Logic.GMailSender;
import com.example.tradoid.Business_Logic.Utils;
import com.example.tradoid.Business_Logic.emailTextWatcher;
import com.example.tradoid.Business_Logic.passwordTextWatcher;
import com.example.tradoid.firebase.model.SignInViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class Sign_In extends AppCompatActivity {

    public String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.sign_in_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(login.class, ""));

        // Making Text View "Sign Up" Clickable
        TextView tv_sign_up = findViewById(R.id.tv_from_sign_in_to_sign_up);
        tv_sign_up.setOnClickListener(v -> sendToActivity(Sign_Up.class, ""));

        // TextInputLayouts
        TextInputLayout email_layout = findViewById(R.id.textInputLayout_email_sign_in);
        TextInputLayout password_layout = findViewById(R.id.textInputLayout_password_sign_in);

        //All TextInput Editors
        TextInputEditText et_email = findViewById(R.id.edit_text_sign_in_email);
        TextInputEditText et_password = findViewById(R.id.edit_text_sign_in_password);

        // Connecting to Error Msg Text view
        TextView errortv = findViewById(R.id.tv_error_msg_sign_in);

        // Making the Text View "Forgot your password?" Clickable
        TextView tv_forgot_pass = findViewById(R.id.tv_forgot_pass_sign_in);
        tv_forgot_pass.setOnClickListener(v -> sendMail(String.valueOf(et_email.getText()), errortv));

        // Error Text Watchers for invalid input
        et_email.addTextChangedListener(new emailTextWatcher(email_layout));
        et_password.addTextChangedListener(new passwordTextWatcher(password_layout));

        //Connecting the Sign in Button
        Button btn_sign_up = findViewById(R.id.btn_sign_in);
        btn_sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // connect to view model
                SignInViewModel viewModel = new ViewModelProvider(Sign_In.this).get(SignInViewModel.class);

                // Check if all fields are filled
                if(et_email.getText().length() == 0)
                    email_layout.setError("Field Required");
                if(et_password.getText().length() == 0)
                    password_layout.setError("Field Required");

                // Check if all fields have no error TODO change to setting a tv
                if(email_layout.getError() == null && password_layout.getError() == null){
                    email = et_email.getText().toString();
                    password = et_password.getText().toString();

                    // checking if the user exists
                    viewModel.reset();

                    viewModel.signInTryUsers(email, password);
                    viewModel.singInTryAdmins(email, password);

                    viewModel.getIsUser().observe(Sign_In.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean){
                                viewModel.getUserId().observe(Sign_In.this, new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        viewModel.getIsBanned().observe(Sign_In.this, new Observer<Boolean>() {
                                            @Override
                                            public void onChanged(Boolean aBoolean) {
                                                if (aBoolean){
                                                    sendToActivity(Ban_msg.class, s);
                                                } else {
                                                    sendToActivity(Stock_Market.class, s);
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                viewModel.getIsAdmin().observe(Sign_In.this, new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if (aBoolean){
                                            viewModel.getUserId().observe(Sign_In.this, new Observer<String>() {
                                                @Override
                                                public void onChanged(String s) {
                                                    sendToActivity(User_List.class, s);
                                                }
                                            });
                                        } else{
                                            errortv.setText("Incorrect username or password");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    //send the email, second way
    private void sendMail(String email, TextView errorTv) {

        // check if the mail is valid
        if(!validMail(email)){
            String error = "No such a User exits";
            errorTv.setText(error);
            return;
        }

        // The user Exists
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Utils.EMAIL, Utils.PASSWORD);
                    sender.sendMail("Forgotten Password",
                            "Fuck me, why should i tell you?",
                            "tradoidapp@gmail.com",
                            email); // Who gets the email
                    dialog.dismiss();
                } catch (Exception e) {
                    System.out.println("DID NOT WORK");
                }
            }
        });
        sender.start();
    }

    // checks if the users email is valid if the user forgot his password
    public boolean validMail(String email){
        return true;
    }

    // Sends to other screens
    public void sendToActivity(Class cls, String userId){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID", userId);
        startActivity(intent);
    }
}