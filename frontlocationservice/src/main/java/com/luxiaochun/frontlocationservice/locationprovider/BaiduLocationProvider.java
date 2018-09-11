package com.luxiaochun.frontlocationservice.locationprovider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.luxiaochun.frontlocationservice.service.LocationCallback;

/**
 * ProjectName: LocationFrontService
 * PackageName: com.luxiaochun.frontlocationservice.locationprovider
 * Author: jun
 * Date: 2018-09-10 17:11
 * Copyright: (C)HESC Co.,Ltd. 2016. All rights reserved.
 */
public class BaiduLocationProvider {
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private LocationCallback listener;
    private static BaiduLocationProvider instance;
    private int intervalTime = 10 * 1000;

    public static synchronized BaiduLocationProvider getInstance() {
        if (instance == null) {
            instance = new BaiduLocationProvider();
        }
        return instance;
    }

    private BaiduLocationProvider() {
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    /**
     * 开始定位
     */
    public void startLocation(Context mContext, LocationCallback listener) {
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //定位SDK能够返回三种坐标类型的经纬度（国内），分别是GCJ02（国测局坐标）、BD09（百度墨卡托坐标）和BD09ll（百度经纬度坐标）。
        option.setCoorType("bd09ll");
        //可选，设置发起定位请求的间隔，int类型，单位ms
        option.setScanSpan(intervalTime);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.listener = listener;
        mLocationClient.start();
    }

    /**
     * 停止定位服务
     */
    public void stopLocation() {
        if (mLocationClient == null)
            return;
        mLocationClient.stop();
        instance = null;
    }

    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null && listener != null) {
                listener.onLocation(location.getLatitude() + "", location.getLongitude() + "");
            }
        }
    }
}
