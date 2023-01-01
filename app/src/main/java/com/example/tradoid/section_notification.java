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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.tradoid.receiver.ReminderBroadcast;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

public class section_notification extends AppCompatActivity {

    String user_ID;

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

        Button btn = findViewById(R.id.notification_btn);
        btn.setOnClickListener(v -> {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "IT WAS ONLY A TEESSSTTTT **runs away**")
                    .setSmallIcon(R.drawable.ic_logout)
                    .setContentTitle("Test")
                    .setContentText("It was only a test Bro.. only a test calm down! Wait put that fucking Axe down")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(v -> {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Come her you ***")
                    .setSmallIcon(R.drawable.ic_logout)
                    .setContentTitle("Im Coming for you 2")
                    .setContentText("Come don't run, my axe johny just wan't to talk about cutting edge technology")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(2, builder.build());
        });


        Intent intent = new Intent(section_notification.this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(section_notification.this,0,intent,PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 20);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timaAtButtonClick = System.currentTimeMillis();

        // initiate a Switch
        SwitchMaterial testSwitch = findViewById(R.id.switch1);

        //Save Switch State in shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("testSwitch", MODE_PRIVATE );
        testSwitch.setChecked(sharedPreferences.getBoolean("value", true));

        testSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = getSharedPreferences("testSwitch",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                }
                else{
                    SharedPreferences.Editor editor = getSharedPreferences("testSwitch",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                }
            }
        });


        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(v->{
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 60 * 1000,pendingIntent);
        });

        Button cancelBtn3 = findViewById(R.id.button4);
        cancelBtn3.setOnClickListener(v -> {
            alarmManager.cancel(pendingIntent);
        });

    }

    //Creates the Notification Channels
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test";
            String description = "It was just a Test wuhuu ** cries **";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel test_channel = new NotificationChannel("IT WAS ONLY A TEESSSTTTT **runs away**", name, importance);
            NotificationChannel Come_channel = new NotificationChannel("Come her you ***", "Come", importance);
            NotificationChannel rest_channel = new NotificationChannel("Shut UP", "FUCK", importance);
            test_channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(test_channel);
            notificationManager.createNotificationChannel(Come_channel);
            notificationManager.createNotificationChannel(rest_channel);
        }
    }

    // Sends to other screens
    public void sendToActivity(Class cls){
        Intent intent = new Intent(this,cls);
        intent.putExtra("user_ID",user_ID);
        startActivity(intent);
    }

//    private void createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

}