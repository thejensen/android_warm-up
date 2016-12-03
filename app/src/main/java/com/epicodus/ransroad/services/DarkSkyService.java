package com.epicodus.ransroad.services;

import android.util.Log;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.models.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jensese on 12/2/16.
 */

public class DarkSkyService {
    public static final String TAG = DarkSkyService.class.getSimpleName();

    public static void findWeather(String location, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.DARK_SKY_BASE_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.DARK_SKY_API_KEY);
        urlBuilder.addPathSegment(location);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Weather> processResults(Response response) {
        ArrayList<Weather> weatherResults = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject weatherJSON = new JSONObject(jsonData);
                JSONObject currentWeatherJSON = weatherJSON.getJSONObject("currently");
                String summary = currentWeatherJSON.getString("summary");
                String temperature = currentWeatherJSON.getString("temperature") + " F";
                String tempFeelsLike = currentWeatherJSON.getString("apparentTemperature") + " F";
                String precipIntensity = "Precipitation Intensity is " + currentWeatherJSON.getString("precipIntensity");
                String precipProbability = "Precipitation Probability is " + currentWeatherJSON.getString("precipProbability");
                String windSpeed = "Wind Speed | " + currentWeatherJSON.getString("windSpeed");
                String windBearing = "Wind Bearing | " + currentWeatherJSON.getString("windBearing");

                Weather weather = new Weather(summary, temperature, tempFeelsLike, precipIntensity, precipProbability, windSpeed, windBearing);

                weatherResults.add(weather);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherResults;
    }
}