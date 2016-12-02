package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.epicodus.ransroad.models.Weather;
import com.epicodus.ransroad.services.DarkSkyService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = WeatherActivity.class.getSimpleName();
    @Bind(R.id.weatherTitleTextView) TextView mWeatherTitleTextView;
    @Bind(R.id.getClothingButton) Button mGetClothingButton;
    @Bind(R.id.listView) ListView mListView;

    public ArrayList<Weather> mWeathers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mWeatherTitleTextView.setTypeface(seasideFont);

        Intent intent = getIntent();
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");

        mGetClothingButton.setOnClickListener(this);
        getWeather(latitude, longitude);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(WeatherActivity.this, ClothingActivity.class);
        startActivity(intent);
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

                Log.v(TAG, "mWeathers: " + mWeathers);
                WeatherActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String[] weatherTemperatures = new String [mWeathers.size()];
                        for (int i = 0; i < weatherTemperatures.length; i++) {
                            weatherTemperatures[i] = mWeathers.get(i).getmTemperature();
                        }
                        ArrayAdapter adapter = new ArrayAdapter(WeatherActivity.this, android.R.layout.simple_list_item_1, weatherTemperatures);
                        mListView.setAdapter(adapter);

                        for (Weather weather : mWeathers) {
                            Log.d(TAG, "Summary: " + weather.getmSummary());
                            Log.d(TAG, "Precip Intensity: " + weather.getmPrecipIntensity());
                            Log.d(TAG, "Precip Probability: " + weather.getmPrecipProbability());
                            Log.d(TAG, "Feels like: " + weather.getmTempFeelsLike());
                            Log.d(TAG, "Wind bearing: " + weather.getmWindBearing());
                            Log.d(TAG, "Wind speed: " + weather.getmWindSpeed());
                        }
                    }
                });
            }
        });
    }
}
