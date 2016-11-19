package com.epicodus.ransroad;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.titleTextView) TextView mTitleTextView;
    @Bind(R.id.getWeatherButton) Button mGetWeatherButton;
    @Bind(R.id.latitudeEditText) EditText mLatitudeEditText;
    @Bind(R.id.longitudeEditText) EditText mLongitudeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mTitleTextView.setTypeface(seasideFont);

        mGetWeatherButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String latitude = mLatitudeEditText.getText().toString();
        String longitude = mLongitudeEditText.getText().toString();
        if(mLatitudeEditText.getText().toString().length() == 0) {
            mLatitudeEditText.setError("Latitude is required");
        }
        else if(mLongitudeEditText.getText().toString().length() == 0) {
            mLongitudeEditText.setError("Longitude is required");
        }
        else {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        }
    }
}
