package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.example.tradoid.Business_Logic.GMailSender;
import com.example.tradoid.Business_Logic.Utils;

import java.util.Random;

public class EmailValidation extends AppCompatActivity {

    int val_code;
    String email;
    Boolean confirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_validation);

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.email_val_back_arrow);
        tv_back_arrow.setOnClickListener(v -> startActivity(new Intent(this,Sign_In.class)));

        // Get user email
        email ="";
        if (getIntent().hasExtra("email")){email = getIntent().getStringExtra("email");}

        // Add Email to text msg
        TextView textMsg = findViewById(R.id.tv_msg_email_val);
        String msg_email = textMsg.getText() + " " + email;
        textMsg.setText(msg_email);

        // Connecting to Error Msg Text view
        TextView error_tv = findViewById(R.id.tv_error_msg_email_val);

        // Continue Button - TODO finish
        Button continue_btn = findViewById(R.id.btn_email_val);
        continue_btn.setOnClickListener(v -> {
            if (confirmed){
                Intent to_res_password = new Intent(this,ResetPassword.class);
                to_res_password.putExtra("email",email);
                startActivity(to_res_password);
            }
            else{
                String error_msg = "Please enter the validation code";
                error_tv.setText(error_msg);
            }
        });

        // Get the validation code
        if (getIntent().hasExtra("val_code")){val_code = getIntent().getIntExtra("val_code",123456);}

        // Test Pin value
        final PinEntryEditText pinEntry = findViewById(R.id.validation_pin);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(str -> {
                if (Integer.parseInt(str.toString()) == val_code) {
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    confirmed = true;
                } else {
                    Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                    pinEntry.setText(null);
                }
            });
        }

        // Implementing the resend
        TextView tv_resend = findViewById(R.id.tv_resend_email_val);
        tv_resend.setOnClickListener(v -> {
            resend();
            assert pinEntry != null;
            pinEntry.setText(null);
        });
    }

    //send the email, second way
    private void resend() {

        // The user Exists
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        // Sending the Mail
        Thread sender = new Thread(() -> {
            try {
                // Random validation code
                Random random = new Random();
                val_code = random.nextInt(1000000);

                // Adding missing zero prefixes of the validation code
                StringBuilder code = new StringBuilder(String.valueOf(val_code));
                while( code.length() < 6){
                    code.insert(0, "0");
                }

                // Send the email
                GMailSender sender1 = new GMailSender(Utils.EMAIL, Utils.PASSWORD);
                sender1.sendMail("Forgotten Password",
                        "Validation code is " + code,
                        "tradoidapp@gmail.com",
                        email); // Who gets the email
                dialog.dismiss();
            } catch (Exception e) {
                System.out.println("DID NOT WORK");
            }
        });
        sender.start();
    }
}