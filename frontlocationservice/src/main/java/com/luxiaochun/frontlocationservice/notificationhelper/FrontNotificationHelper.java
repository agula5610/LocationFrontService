package com.luxiaochun.frontlocationservice.notificationhelper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;


/**
 * ProjectName: JiuZhou
 * PackageName: com.example.jun.jiuzhou.AppUpdateUtil
 * Author: jun
 * Date: 2018-08-08 11:45
 */
public class FrontNotificationHelper {
    private final Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private static final String CHANNEL_ID = "front_service";
    private static final CharSequence CHANNEL_NAME = "front_service_channel";

    private FrontNotificationHelper(Context mContext) {
        this.mContext = mContext;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        setUpNotification();
    }

    public static FrontNotificationHelper getIstance(Context mContext) {
        return new FrontNotificationHelper(mContext);
    }

    /**
     * 创建通知
     */
    public void setUpNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        }
        mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
    }

    /**
     * 获取通知
     * @return
     */
    public Notification getNotification() {
        return mBuilder == null ? null : mBuilder.build();
    }
}
