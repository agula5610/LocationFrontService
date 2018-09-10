package com.luxiaochun.frontlocationservice;

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
public class FrontNotificationManager {
    private final Context mContext;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Notification notification;
    private static final String CHANNEL_ID = "front_service";
    private static final CharSequence CHANNEL_NAME = "front_service_channel";
    private static final int NOTIFY_ID = 1;

    private FrontNotificationManager(Context mContext) {
        this.mContext = mContext;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static FrontNotificationManager getIstance(Context mContext) {
        return new FrontNotificationManager(mContext);
    }

    /**
     * 创建通知
     */
    public void setUpNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //设置绕过免打扰模式
//            channel.setBypassDnd(false);
//            //检测是否绕过免打扰模式
//            channel.canBypassDnd();
//            //设置在锁屏界面上显示这条通知
//            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
//            channel.setLightColor(Color.GREEN);
//            channel.setShowBadge(true);
//            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.enableVibration(false);
            channel.enableLights(false);
            mNotificationManager.createNotificationChannel(channel);
        }

        mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
        mBuilder.setContentTitle("前台服务正在运行……")
                .setContentText("正在进行定位服务")
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        notification = mBuilder.build();
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
    }

    /**
     * 取消通知
     */
    public void cancel() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFY_ID);
        }
    }
}
