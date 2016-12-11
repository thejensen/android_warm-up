package com.epicodus.ransroad.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.epicodus.ransroad.adapter.ClothingPagerAdapter;
import com.epicodus.ransroad.models.Clothing;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ClothingDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private ClothingPagerAdapter adapterViewPager;
    ArrayList<Clothing> mClothings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_detail);
        ButterKnife.bind(this);

        mClothings = Parcels.unwrap(getIntent().getParcelableExtra("clothings"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new ClothingPagerAdapter(getSupportFragmentManager(), mClothings);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
