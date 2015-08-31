package com.xiaopeng.android_service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by xiaopeng wang on 2015/8/31.
 */
public class LocalService extends Service {

    private static String TAG = "LocalService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();

        // bring the service to foreground
        if(Build.VERSION.SDK_INT >= 16){
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, NewActivity.class), PendingIntent.FLAG_ONE_SHOT);

            Notification notification = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("title")
                    .setContentText("describe")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .build();

            startForeground(1, notification);

        }else if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 16){
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, NewActivity.class), PendingIntent.FLAG_ONE_SHOT);
            Notification.Builder builder = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("title")
                    .setContentText("describe")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true);
            Notification notification = builder.build();
            startForeground(1, notification);

        }else if(Build.VERSION.SDK_INT <= 11) {
            Notification notification = new Notification(R.drawable.ic_launcher, "Foreground Service Started.", System.currentTimeMillis());
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, NewActivity.class), 0);
            notification.setLatestEventInfo(this, "Foreground Service", "Foreground Service Started.", contentIntent);
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.d(TAG, "onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();

        stopForeground(true);
    }
}
