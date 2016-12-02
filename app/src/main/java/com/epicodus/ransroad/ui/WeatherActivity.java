package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.ransroad.DarkSkyService.DarkSkyService;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = WeatherActivity.class.getSimpleName();
    @Bind(R.id.weatherTitleTextView) TextView mWeatherTitleTextView;
    @Bind(R.id.weatherTextView) TextView mWeatherTextView;
    @Bind(R.id.getClothingButton) Button mGetClothingButton;
    public String mWeather = null;

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
        mWeatherTextView.setText("This would be the weather information for " + latitude + ", " + longitude);

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
                mWeather = darkSkyService.processResults(response);
            }
        });
    }
}
