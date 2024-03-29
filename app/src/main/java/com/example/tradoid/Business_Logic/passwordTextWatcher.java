package com.example.tradoid.Business_Logic;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class passwordTextWatcher implements TextWatcher {

    TextInputLayout layout;

    public passwordTextWatcher(TextInputLayout layout) {
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
        else if (s.length() < 8)
            layout.setError("Invalid Input: Min character length is 8");
        else if (s.length() > 16)
            layout.setError("Invalid Input: Max character length is 16");
        else
            layout.setError(null);
    }
}
