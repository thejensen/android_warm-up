package com.epicodus.ransroad.services;

import com.epicodus.ransroad.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

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

}


//https://www.zipcodeapi.com/rest/D3ux9BdArhcSljQ3pMN73rz9oC7MJZln2QUcr7NSp0LKjBNJoARs2rBr3JeNIzaL/info.json/97211/degrees