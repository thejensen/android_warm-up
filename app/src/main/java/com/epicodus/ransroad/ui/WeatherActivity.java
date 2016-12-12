package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.adapter.WeatherListAdapter;
import com.epicodus.ransroad.models.Location;
import com.epicodus.ransroad.models.Weather;
import com.epicodus.ransroad.services.DarkSkyService;
import com.epicodus.ransroad.services.ZipCodeAPIService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = WeatherActivity.class.getSimpleName();

    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;

    @Bind(R.id.weatherTitleTextView) TextView mWeatherTitleTextView;
    @Bind(R.id.getClothingButton) Button mGetClothingButton;
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private WeatherListAdapter mAdapter;

    public ArrayList<Weather> mWeathers = new ArrayList<>();
    public ArrayList<Location> mLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mWeatherTitleTextView.setTypeface(seasideFont);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        Log.d("SharedPref Location", mRecentAddress);

        Intent intent = getIntent();
        String zipcode = intent.getStringExtra("zipcode");

        mGetClothingButton.setOnClickListener(this);

        getLatLong(zipcode);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(WeatherActivity.this, ClothingListActivity.class);
        startActivity(intent);
    }

    private void getLatLong(String zipcode) {
        final ZipCodeAPIService zipCodeAPIService = new ZipCodeAPIService();
        zipCodeAPIService.findLatLong(zipcode, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mLocations = ZipCodeAPIService.processLocationResults(response);
                String latitude = mLocations.get(0).getLatitude();
                String longitude = mLocations.get(0).getLongitude();
                Log.v(TAG, "Lat and long are" + latitude + " & " + longitude);

                getWeather(latitude, longitude);
            }
        });
    }

    private void getWeather(String latitude, String longitude) {
        final DarkSkyService darkSkyService = new DarkSkyService();
        String location = latitude + "," + longitude;
        darkSkyService.findWeather(location, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mWeathers = darkSkyService.processResults(response);

                WeatherActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new WeatherListAdapter(mWeathers, getApplicationContext());
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WeatherActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}
