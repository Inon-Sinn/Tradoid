package com.example.tradoid.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.tradoid.R;
import com.example.tradoid.Sign_In;

public class DailyReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent toAppIntent = new Intent(context, Sign_In.class);
        PendingIntent toAppPendingIntent = PendingIntent.getActivity(context, 0, toAppIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Daily")
                .setSmallIcon(R.drawable.ic_app_sym_black)
                .setContentTitle("Daily Review")
                .setContentText("See the changes that happened today")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(toAppPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
