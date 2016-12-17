package com.epicodus.ransroad.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.ransroad.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Bind(R.id.titleTextView) TextView mTitleTextView;
    @Bind(R.id.getWeatherButton) Button mGetWeatherButton;
    @Bind(R.id.zipcodeEditText) EditText mZipcodeEditText;
    @Bind(R.id.poweredByTextView) TextView mPoweredByTextView;
    @Bind(R.id.createAccountTextView) TextView mCreateAccountTextView;
    @Bind(R.id.loginTextView) TextView mLoginTextView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        // TODO: figure out logout... maybe if shared preferences does not contain username, then they go to main activity, and shared prefs has to contain BOTH loc and un to skip home screen.
//        if (mSharedPreferences.contains(Constants.PREFERENCES_LOCATION_KEY)) {
//            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
//            startActivity(intent);
//        }

        Typeface seasideFont = Typeface.createFromAsset(getAssets(), "fonts/seaside_font.ttf");
        mTitleTextView.setTypeface(seasideFont);

        mGetWeatherButton.setOnClickListener(this);
        mPoweredByTextView.setOnClickListener(this);
        mCreateAccountTextView.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                    mCreateAccountTextView.setVisibility(View.GONE);
                    mLoginTextView.setVisibility(View.GONE);
                } else {

                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mGetWeatherButton) {
            String zipcode = mZipcodeEditText.getText().toString();
            if (!(zipcode).equals("")) {
                addToSharedPreferences(zipcode);
            } else {
                mZipcodeEditText.setError("The weather needs to know where you are. ;)");
            }
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
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
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        Toast.makeText(MainActivity.this, "You are now logged out. Until next time!", Toast.LENGTH_LONG).show();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void addToSharedPreferences(String zipcode) {
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, zipcode).apply();
    }

}
