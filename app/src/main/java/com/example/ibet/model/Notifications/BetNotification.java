package com.example.ibet.model.Notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;


import com.example.ibet.R;

public class BetNotification extends BroadcastReceiver {
    public final String CHANNEL_ID = "2";
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"2", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ibetlogo)
                .setContentTitle("iBet")
                .setContentText("Come And Check Your New Rank!");
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        compat.notify(2,builder.build());
    }
}
