package com.epicodus.ransroad;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.weatherTitleTextView) TextView mWeatherTitleTextView;
    @Bind(R.id.weatherTextView) TextView mWeatherTextView;
    @Bind(R.id.getClothingButton) Button mGetClothingButton;

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
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(WeatherActivity.this, ClothingActivity.class);
        startActivity(intent);
    }
}
