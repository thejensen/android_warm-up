package com.epicodus.ransroad.models;

/**
 * Created by jensese on 12/11/16.
 */

public class Location {
    private String mLatitude;
    private String mLongitude;
    private String mCity;
    private String mState;

    public Location(String mState, String mCity, String mLongitude, String mLatitude) {
        this.mState = mState;
        this.mCity = mCity;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }
}
