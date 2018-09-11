package com.luxiaochun.frontlocationservice.locationprovider;

import android.content.Context;

import com.luxiaochun.frontlocationservice.service.LocationCallback;

/**
 * ProjectName: LocationFrontService
 * PackageName: com.luxiaochun.frontlocationservice
 * Author: jun
 * Date: 2018-09-10 11:14
 */
public class LocationHelper {
    /**
     * @param mContext     上下文
     * @param intervalTime 间隔时间
     * @param listener     位置监听
     */
//    public static void startLocation(Context mContext, long intervalTime, LocationCallback listener) {
//        GPSLocationProvider provider = GPSLocationProvider.getInstance();
//        if (intervalTime != 0)
//            provider.setIntervalTime(intervalTime);
//        provider.startLocation(mContext, listener);
//    }

    /**
     * 停止定位，主要是为了省电
     */
//    public static void stopLocation() {
//        GPSLocationProvider.getInstance().stopLocation();
//    }


    //=========================百度定位======分割线===============//
    /**
     * @param mContext     上下文
     * @param intervalTime 间隔时间
     * @param listener     位置监听
     */
    public static void startLocation(Context mContext, int intervalTime, LocationCallback listener) {
        BaiduLocationProvider provider = BaiduLocationProvider.getInstance();
        if (intervalTime != 0)
            provider.setIntervalTime(intervalTime);
        provider.startLocation(mContext, listener);
    }

    /**
     * 停止定位，主要是为了省电
     */
    public static void stopLocation() {
        BaiduLocationProvider.getInstance().stopLocation();
    }
}
