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
public class RawLocationProvider {
    private LocationManager locationManager;
    private LocationCallback listener;
    private static RawLocationProvider instance;
    private Context mContext;

    public static synchronized RawLocationProvider getInstance(Context mContext, LocationCallback listener) {
        if (instance == null) {
            instance = new RawLocationProvider(mContext, listener);
        }
        return instance;
    }

    private RawLocationProvider(Context mContext, LocationCallback listener) {
        this.mContext = mContext;
        this.listener = listener;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 开始定位
     */
    public void startLocation() {
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
        locationManager.requestLocationUpdates(best, 3000, 0, MyLocationListener);
    }

    /**
     * 停止定位服务
     */
    public void stopLocation() {
        if (MyLocationListener == null)
            return;
        locationManager.removeUpdates(MyLocationListener);
    }

    /**
     * 位置监听
     */
    private LocationListener MyLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (listener != null && location != null) {
                listener.onLocation(location);
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
