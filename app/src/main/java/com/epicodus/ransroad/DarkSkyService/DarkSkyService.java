package com.epicodus.ransroad.DarkSkyService;

import android.util.Log;

import com.epicodus.ransroad.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

    public String processResults(Response response) {
        String weather = null;
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                Log.v(TAG, "SUCCESSSSSful response");
                JSONObject weatherObject = new JSONObject(jsonData);
                JSONObject resultsJSON = weatherObject.getJSONObject("currently");
                String temperature = resultsJSON.getString("temperature");
                Log.v(TAG, "resultsJson: " + resultsJSON + ", yay!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}