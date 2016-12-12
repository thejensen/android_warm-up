package com.epicodus.ransroad.models;

/**
 * Created by jensese on 12/11/16.
 */

public class Location {
    private String mLatitude;
    private String mLongitude;

    public Location(String mLatitude, String mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }
}
