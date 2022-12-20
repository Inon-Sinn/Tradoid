package com.example.tradoid.Others;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class usernameTextWatcher implements TextWatcher {

    TextInputLayout layout;

    public usernameTextWatcher(TextInputLayout layout) {
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
        String t = s.toString();
        if(s.length() == 0)
            layout.setError("Field Required");
        else if (s.length() < 3)
            layout.setError("Invalid Input: Min character length is 3");
        else if (s.length() > 8)
            layout.setError("Invalid Input: Max character length is 8");
        else if (t.contains(" ")){
            layout.setError("Invalid Input: Username may not contain spaces!");
        }
        else layout.setError(null);
    }
}