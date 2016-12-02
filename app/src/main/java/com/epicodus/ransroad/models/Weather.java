package com.epicodus.ransroad.models;

/**
 * Created by jensese on 12/2/16.
 */

public class Weather {
    private String mSummary;
    private String mTemperature;
    private String mTempFeelsLike;
    private String mPrecipIntensity;
    private String mPrecipProbability;
    private String mWindSpeed;
    private String mWindBearing;

    public Weather(String mSummary, String mTemperature, String mTempFeelsLike, String mPrecipIntensity, String mPrecipProbability, String mWindSpeed, String mWindBearing) {
        this.mSummary = mSummary;
        this.mTemperature = mTemperature;
        this.mTempFeelsLike = mTempFeelsLike;
        this.mPrecipIntensity = mPrecipIntensity;
        this.mPrecipProbability = mPrecipProbability;
        this.mWindSpeed = mWindSpeed;
        this.mWindBearing = mWindBearing;
    }

    public String getmSummary() {
        return mSummary;
    }

    public String getmTemperature() {
        return mTemperature;
    }

    public String getmTempFeelsLike() {
        return mTempFeelsLike;
    }

    public String getmPrecipIntensity() {
        return mPrecipIntensity;
    }

    public String getmPrecipProbability() {
        return mPrecipProbability;
    }

    public String getmWindSpeed() {
        return mWindSpeed;
    }

    public String getmWindBearing() {
        return mWindBearing;
    }
}
