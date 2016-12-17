package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ransroad.Constants;
import com.epicodus.ransroad.adapter.LocationListAdapter;
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
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;

    @Bind(R.id.weatherTitleTextView) TextView mWeatherTitleTextView;
    @Bind(R.id.getClothingButton) Button mGetClothingButton;
    @Bind(R.id.weatherRecyclerView) RecyclerView mWeatherRecyclerView;
    @Bind(R.id.locationRecyclerView) RecyclerView mLocationRecyclerView;
    private WeatherListAdapter mAdapter;
    private LocationListAdapter mLocationAdapter;

    public ArrayList<Weather> mWeathers = new ArrayList<>();
    public ArrayList<Location> mLocations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mWeatherTitleTextView.setTypeface(seasideFont);

        mGetClothingButton.setOnClickListener(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        if (mRecentAddress != null) {
            getLatLong(mRecentAddress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);
                getLatLong(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
                if (mLocations.size() == 0) {
                  new Thread() {
                        public void run() {
                            WeatherActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(WeatherActivity.this, "Sorry, the zip code entered is invalid. Please use the search bar again to try a new zip code.", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }.start();
                } else {
                    String latitude = mLocations.get(0).getLatitude();
                    String longitude = mLocations.get(0).getLongitude();
                    getWeather(latitude, longitude);
                }
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
                        mWeatherRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(WeatherActivity.this);
                        mWeatherRecyclerView.setLayoutManager(layoutManager);
                        mWeatherRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }

    private void addToSharedPreferences(String zipcode) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, zipcode).apply();
    }
}
