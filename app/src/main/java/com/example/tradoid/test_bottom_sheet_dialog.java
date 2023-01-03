package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.alimuzaffar.lib.pin.PinEntryEditText;

public class test_bottom_sheet_dialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_bottom_sheet_dialog);

        final PinEntryEditText pinEntry = findViewById(R.id.validation_pin);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(str -> {
                if (str.toString().equals("123456")) {
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                    pinEntry.setText(null);
                }
            });
        }

    }

}