package com.epicodus.ransroad.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.ransroad.models.Location;
import com.epicodus.ransroad.ui.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jensese on 12/13/16.
 */

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationViewHolder> {
    private ArrayList<Location> mLocations = new ArrayList<>();
    private Context mContext;

    public LocationListAdapter(Context mContext, ArrayList<Location> mLocations) {
        this.mContext = mContext;
        this.mLocations = mLocations;
    }

    @Override
    public LocationListAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        LocationViewHolder viewHolder = new LocationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        holder.bindLocation(mLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cityTextView) TextView mCityTextView;
        @Bind(R.id.stateTextView) TextView mStateTextView;
        private Context mContext;

        public LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindLocation(Location location) {
            mCityTextView.setText(location.getCity());
            mStateTextView.setText(location.getState());
        }
    }
}