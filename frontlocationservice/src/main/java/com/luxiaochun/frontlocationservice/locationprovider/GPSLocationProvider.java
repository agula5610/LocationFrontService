package com.luxiaochun.frontlocationservice.locationprovider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.luxiaochun.frontlocationservice.service.LocationCallback;

/**
 * ProjectName: LocationFrontService
 * PackageName: com.luxiaochun.frontlocationservice.locationprovider
 * Author: jun
 * Date: 2018-09-10 17:11
 */
public class GPSLocationProvider {
    private LocationManager locationManager;
    private LocationCallback listener;
    private static GPSLocationProvider instance;
    private long intervalTime = 10 * 1000;

    public static synchronized GPSLocationProvider getInstance() {
        if (instance == null) {
            instance = new GPSLocationProvider();
        }
        return instance;
    }

    private GPSLocationProvider() {

    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    /**
     * 开始定位
     */
    public void startLocation(Context mContext, LocationCallback listener) {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String best = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        locationManager.requestLocationUpdates(best, intervalTime, 0, MyLocationListener);
    }

    /**
     * 停止定位服务
     */
    public void stopLocation() {
        if (locationManager == null || MyLocationListener == null)
            return;
        locationManager.removeUpdates(MyLocationListener);
        instance = null;
    }

    /**
     * 位置监听
     */
    private LocationListener MyLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (listener != null && location != null) {
                listener.onLocation(location.getLatitude() + "", location.getLongitude() + "");
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
