package com.epicodus.ransroad.DarkSkyService;

import com.epicodus.ransroad.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jensese on 12/2/16.
 */

public class DarkSkyService {
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
}
