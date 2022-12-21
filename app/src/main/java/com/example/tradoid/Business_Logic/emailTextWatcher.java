package com.example.tradoid.Business_Logic;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class emailTextWatcher implements TextWatcher {

    TextInputLayout layout;

    public emailTextWatcher(TextInputLayout layout) {
        this.layout = layout;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() == 0)
            layout.setError("Field Required");
        else{
            String email = s.toString();
            String regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                    + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
            if(!patternMatches(email, regexPattern)){
                layout.setError("Invalid Email address");
            }
            else
                layout.setError(null);
        }
    }

    // function that matches Patterns - https://www.baeldung.com/java-email-validation-regex
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}


