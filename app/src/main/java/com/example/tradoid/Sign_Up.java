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
import android.widget.ImageView;
import android.widget.TextView;

public class Sign_Up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Creating a custom Toolbar
        Toolbar sign_in_toolbar = findViewById(R.id.toolbar_sign_up);
        setSupportActionBar(sign_in_toolbar);

        // Implementing the Back arrow in the Toolbar
        ImageView back_arrow = findViewById(R.id.sign_up_To_login);
        back_arrow.setOnClickListener(v -> sendToActivity(login.class));

        // Connecting to the Text View "sign in"
        TextView tv_sign_in = findViewById(R.id.tv_from_sign_up_to_sign_in);

        // Making the Text View Clickable -> sends to Sign In
        String text = "Sign In";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                sendToActivity(Sign_In.class);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE);
            }
        };
        BackgroundColorSpan bcs = new BackgroundColorSpan(Color.WHITE);
        ss.setSpan(clickableSpan,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(bcs,0,text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Bacground stays white when clicked
        tv_sign_in.setText(ss);
        tv_sign_in.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        startActivity(intent);
    }
}