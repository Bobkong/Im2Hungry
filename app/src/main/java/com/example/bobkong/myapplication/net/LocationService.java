package com.example.bobkong.myapplication.net;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Bob on 2018/6/18.
 */

public class LocationService {

    private static final String LOG_TAG = LocationService.class.getSimpleName();
    private static LocationService sInstance;
    private final AMapLocationClient mLocationClient;
    private final AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            mLat = aMapLocation.getLatitude();
            mLng = aMapLocation.getLongitude();
            Log.d(LOG_TAG,"Lat: " + mLat + "Lng" + mLng);
        }
    };
    private final AMapLocationClientOption mLocationOption;
    private double mLat;
    private double mLng;

    public static LocationService getInstance() {
        return sInstance;
    }

    public static void initInstance(Context context) {
        if (sInstance != null)
            return;
        sInstance = new LocationService(context);
    }

    public LocationService(Context context) {
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    public double getLat() {
        return mLat;
    }

    public double getLng() {
        return mLng;
    }
}
