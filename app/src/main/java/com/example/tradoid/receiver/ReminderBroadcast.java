package com.example.tradoid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tradoid.R;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Shut UP")
                .setSmallIcon(R.drawable.ic_baseline_restore_remove_this)
                .setContentTitle("Shut up its 5'o Clock")
                .setContentText("Just axe him already i want to sleep, ** mumbles** motherf******")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(3, builder.build());
    }
}
