package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class test_bottom_sheet_dialog extends AppCompatActivity {

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bottom_sheet_dialog);

        EditText num_1 = findViewById(R.id.validation_num1);






    }

}