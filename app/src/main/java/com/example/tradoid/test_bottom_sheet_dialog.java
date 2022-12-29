package com.example.tradoid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class test_bottom_sheet_dialog extends AppCompatActivity {

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bottom_sheet_dialog);

        // Connecting to the background lightening
        View bg_lighting = findViewById(R.id.backgroundLight);
        bg_lighting.setVisibility(View.GONE);

        // Connecting to the Bottom sheet itself and its state
        ConstraintLayout mBottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);

        // Connecting to the Title and its icon
        TextView title = findViewById(R.id.bottom_sheet_title);
        ImageView icon = findViewById(R.id.bottom_sheet_title_icon);

        // OnClick Listeners to pull up/down the Bottom sheet Dialog
        title.setOnClickListener(v -> bottomSheetClick());
        icon.setOnClickListener(v -> bottomSheetClick());

        //When the bottom sheet get pulled up - darkens the background and changes the icons direction
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    bg_lighting.setVisibility(View.GONE);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                icon.setRotation(slideOffset * 180);
                bg_lighting.setVisibility(View.VISIBLE);
                bg_lighting.setAlpha(slideOffset);
            }
        });
    }

    // An Auxiliary function that pulls up/down the Bottom sheet dialog when The OnClick listeners were activated
    public void bottomSheetClick(){
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }else{
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }
}