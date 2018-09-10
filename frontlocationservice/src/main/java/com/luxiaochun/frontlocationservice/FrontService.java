package com.luxiaochun.frontlocationservice;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * 后台下载
 */
public class FrontService extends Service {
    private static final String CHANNEL_ID = "front_service";
    private static final CharSequence CHANNEL_NAME = "front_service_channel";
    private static final int NOTIFY_ID = 1;

    public static void startService(Activity context) {
        Intent intent = new Intent(context, FrontService.class);
        context.startService(intent);
    }

    public static void stopService(Activity context) {
        Intent intent = new Intent(context, FrontService.class);
        context.stopService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpNotification();
        Log.i("FrontService", "setUpNotification");
    }

    private void setUpNotification() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(false);
            channel.enableLights(false);
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        Notification notification = mBuilder.build();
        startForeground(NOTIFY_ID, notification);
        LocationHelper helper = LocationHelper.getInstance(this);
        helper.setListener(new LocationCallback() {
            @Override
            public void onLocation(Location location) {
                Log.i("FrontService", "纬度：" + location.getLatitude());
            }
        });
        helper.startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
