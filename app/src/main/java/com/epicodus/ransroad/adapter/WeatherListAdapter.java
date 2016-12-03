package com.epicodus.ransroad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.ransroad.models.Weather;
import com.epicodus.ransroad.ui.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jensese on 12/2/16.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder> {
    private ArrayList<Weather> mWeathers = new ArrayList<>();
    private Context mContext;

    public WeatherListAdapter(ArrayList<Weather> mWeathers, Context mContext) {
        this.mWeathers = mWeathers;
        this.mContext = mContext;
    }

    @Override
    public WeatherListAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item, parent, false);
        WeatherViewHolder viewHolder = new WeatherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeatherListAdapter.WeatherViewHolder holder, int position) {
        holder.bindWeather(mWeathers.get(position));
    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.summaryTextView) TextView mSummaryTextView;
        @Bind(R.id.temperatureTextView) TextView mTemperatureTextView;
        @Bind(R.id.tempFeelsLikeTextView) TextView mFeelsLikeTextView;
        @Bind(R.id.precipIntensityTextView) TextView mPrecipIntensityTextView;
        @Bind(R.id.precipProbabilityTextView) TextView mPrecipProbabilityTextView;
        @Bind(R.id.windSpeedTextView) TextView mWindSpeedTextView;
        @Bind(R.id.windBearingTextView) TextView mWindBearingTextView;
        private Context mContext;

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindWeather(Weather weather) {
            mSummaryTextView.setText(weather.getmSummary());
            mTemperatureTextView.setText(weather.getmTemperature());
            mFeelsLikeTextView.setText(weather.getmTempFeelsLike());
            mPrecipIntensityTextView.setText(weather.getmPrecipIntensity());
            mPrecipProbabilityTextView.setText(weather.getmPrecipProbability());
            mWindSpeedTextView.setText(weather.getmWindSpeed());
            mWindBearingTextView.setText(weather.getmWindBearing());

        }
    }
}
