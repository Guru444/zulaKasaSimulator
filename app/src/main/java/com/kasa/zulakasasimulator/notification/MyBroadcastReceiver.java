package com.kasa.zulakasasimulator.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.view.kasaOpenView.kasaAcilimiMagaza;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, R.raw.ses);
        mp.start();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.kasa_copy)
                        .setContentTitle("Zula Kasa Simülatör")
                        .setContentText("Kasa Açmaya başlayabiliriz..");
        Intent notificationIntent = new Intent(context, kasaAcilimiMagaza.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());


    }

}
