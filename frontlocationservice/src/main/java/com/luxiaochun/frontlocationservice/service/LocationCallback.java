package com.luxiaochun.frontlocationservice.service;

/**
 * ProjectName: LocationFrontService
 * PackageName: com.luxiaochun.frontlocationservice
 * Author: jun
 * Date: 2018-09-10 11:36
 */
public interface LocationCallback {
    void onLocation(String latitude, String longitude);
}
