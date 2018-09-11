package com.luxiaochun.frontlocationservice.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.luxiaochun.frontlocationservice.locationprovider.LocationHelper;
import com.luxiaochun.frontlocationservice.notificationhelper.FrontNotificationHelper;

/**
 * 后台下载
 */
public class FrontService extends Service {
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
        startForegroundNotification();
    }

    /**
     * 开启前台服务，并显示通知
     */
    private void startForegroundNotification() {
        FrontNotificationHelper noHelper = FrontNotificationHelper.getIstance(this);
        if (noHelper.getNotification() != null)
            startForeground(NOTIFY_ID, noHelper.getNotification());
        LocationHelper.startLocation(this, 10000, new LocationCallback() {
            @Override
            public void onLocation(String latitude, String longitude) {
                Log.i("===FrontService===", "纬度：" + latitude + "；经度：" + longitude);
            }
        });
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
        LocationHelper.stopLocation();
        super.onDestroy();
    }
}
