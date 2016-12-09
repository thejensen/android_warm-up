package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.titleTextView) TextView mTitleTextView;
    @Bind(R.id.getWeatherButton) Button mGetWeatherButton;
    @Bind(R.id.latitudeEditText) EditText mLatitudeEditText;
    @Bind(R.id.longitudeEditText) EditText mLongitudeEditText;
    @Bind(R.id.poweredByTextView) TextView mPoweredByTextView;
    @Bind(R.id.createAccountTextView) TextView mCreateAccountTextView;
    @Bind(R.id.loginTextView) TextView mLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mTitleTextView.setTypeface(seasideFont);

        mGetWeatherButton.setOnClickListener(this);
        mPoweredByTextView.setOnClickListener(this);
        mCreateAccountTextView.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mGetWeatherButton) {
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
        if (v == mPoweredByTextView) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://darksky.net/poweredby"));
            startActivity(webIntent);
        }
        if (v == mCreateAccountTextView) {
            Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(intent);
        }
        if (v == mLoginTextView) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "You're logged out! Log in again to see your stuff.", Toast.LENGTH_LONG).show();
    }

}
