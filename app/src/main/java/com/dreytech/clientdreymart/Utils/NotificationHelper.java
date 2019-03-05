package com.dreytech.clientdreymart.Utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import com.dreytech.clientdreymart.R;

public class NotificationHelper extends ContextWrapper {

    private static final String DREY_CHANNEL_ID = "com.dreytech.clientdreymart.DREYTech";
    private static final String DREY_CHANNEL_NAME = "Dreymart";

    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            createChannel();

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel dreyChannel = new NotificationChannel(DREY_CHANNEL_ID, DREY_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        dreyChannel.enableLights(false);
        dreyChannel.enableVibration(true);
        dreyChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(dreyChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager == null)
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getDreymartNotification(String title,
                                                        String message,
                                                        Uri soundUri)
    {
        return new Notification.Builder(getApplicationContext(),DREY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(true);
    }

}
