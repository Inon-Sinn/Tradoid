package com.example.tradoid.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tradoid.R;
import com.example.tradoid.Sign_In;

public class WeeklyReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent toAppIntent = new Intent(context, Sign_In.class);
        PendingIntent toAppPendingIntent = PendingIntent.getActivity(context, 0, toAppIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Weekly")
                .setSmallIcon(R.drawable.ic_app_sym_black)
                .setContentTitle("Weekly Review")
                .setContentText("See the changes that happened this Week")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(10, builder.build());

    }
}
