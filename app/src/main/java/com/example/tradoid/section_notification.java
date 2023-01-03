package com.example.tradoid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.tradoid.receiver.DailyReminderBroadcast;
import com.example.tradoid.receiver.WeeklyReminderBroadcast;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

public class section_notification extends AppCompatActivity {

    String user_ID;

    // Remember each Broadcast has to be added to androidManifest
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_notification);

        // Create the notification channels
        createNotificationChannel();

        // get User ID
        if (getIntent().hasExtra("user_ID")){user_ID = getIntent().getStringExtra("user_ID");}

        // Implementing the Back arrow
        TextView tv_back_arrow = findViewById(R.id.notification_back_arrow);
        tv_back_arrow.setOnClickListener(v -> sendToActivity(Profile.class));

        // initiate the Switches
        SwitchMaterial dailySwitch = findViewById(R.id.Switch_daily);
        SwitchMaterial weeklySwitch = findViewById(R.id.Switch_weekly);

        // Setting the Time for the notifications
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);

        // Daily Reminder

        // Creating a Pending Intent for a BroadCast
        Intent daily_intent = new Intent(section_notification.this, DailyReminderBroadcast.class);
        PendingIntent daily_pendingIntent = PendingIntent.getBroadcast(section_notification.this,0,daily_intent,PendingIntent.FLAG_IMMUTABLE);

        // Create a new Alarm Manager
        AlarmManager dailyAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Save Switch State in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("Switch", MODE_PRIVATE );
        dailySwitch.setChecked(sharedPreferences.getBoolean("value", true));

        dailySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                SharedPreferences.Editor editor = getSharedPreferences("Switch",MODE_PRIVATE).edit();
                editor.putBoolean("value",true);
                editor.apply();
                dailyAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 24 * 60 * 60 * 1000,daily_pendingIntent);
            }
            else{
                SharedPreferences.Editor editor = getSharedPreferences("Switch",MODE_PRIVATE).edit();
                editor.putBoolean("value",false);
                editor.apply();
                dailyAlarmManager.cancel(daily_pendingIntent);
            }
        });

        // Weekly Reminder

        // Creating a Pending Intent for a BroadCast
        Intent weekly_intent = new Intent(section_notification.this, WeeklyReminderBroadcast.class);
        PendingIntent weekly_pendingIntent = PendingIntent.getBroadcast(section_notification.this,0,weekly_intent,PendingIntent.FLAG_IMMUTABLE);

        // Create a new Alarm Manager
        AlarmManager weeklyAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //Save Switch State in shared preferences
        weeklySwitch.setChecked(sharedPreferences.getBoolean("weekly_value", true));

        weeklySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                SharedPreferences.Editor editor = getSharedPreferences("Switch",MODE_PRIVATE).edit();
                editor.putBoolean("weekly_value",true);
                editor.apply();
                weeklyAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),7 * 24 * 60 * 60 * 1000,weekly_pendingIntent);
            }
            else{
                SharedPreferences.Editor editor = getSharedPreferences("Switch",MODE_PRIVATE).edit();
                editor.putBoolean("weekly_value",false);
                editor.apply();
                weeklyAlarmManager.cancel(weekly_pendingIntent);
            }
        });

    }

    //Creates the Notification Channels
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel weekly_channel = new NotificationChannel("Weekly","Weekly Review" , importance);
            NotificationChannel daily_channel = new NotificationChannel("Daily", "Daily Review", importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(weekly_channel);
            notificationManager.createNotificationChannel(daily_channel);
        }
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }
}