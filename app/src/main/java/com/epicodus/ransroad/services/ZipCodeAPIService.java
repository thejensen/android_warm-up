package com.epicodus.ransroad.services;

import android.util.Log;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.models.Location;
import com.epicodus.ransroad.models.Weather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jensese on 12/11/16.
 */

public class ZipCodeAPIService {
    public static final String TAG = ZipCodeAPIService.class.getSimpleName();

    public static void findLatLong(String zipcode, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.ZIPCODEAPI_BASE_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.ZIPCODEAPI_API_KEY);
        urlBuilder.addPathSegment(Constants.ZIPCODEAPI_INFO_QUERY_PARAMETER);
        urlBuilder.addPathSegment(zipcode);
        urlBuilder.addPathSegment(Constants.ZIPCODEAPI_DEGREES_QUERY_PARAMETER);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static ArrayList<Location> processLocationResults(Response response) {
        ArrayList<Location> locationResults = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject locationJSON = new JSONObject(jsonData);

                String latitude = locationJSON.getString("lat");
                String longitude = locationJSON.getString("lng");
                String city = locationJSON.getString("city");
                String state = locationJSON.getString("state");

                Location location = new Location(state, city, longitude, latitude);

                locationResults.add(location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locationResults;
    }

}