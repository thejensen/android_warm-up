package com.epicodus.ransroad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.ransroad.models.Clothing;
import com.epicodus.ransroad.ui.ClothingDetailFragment;

import java.util.ArrayList;

/**
 * Created by jensese on 12/10/16.
 */

public class ClothingPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Clothing> mClothings;

    public ClothingPagerAdapter(FragmentManager fm, ArrayList<Clothing> clothings) {
        super(fm);
        mClothings = clothings;
    }

    @Override
    public Fragment getItem(int position) {
        return ClothingDetailFragment.newInstance(mClothings.get(position));
    }

    @Override
    public int getCount() {
        return mClothings.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mClothings.get(position).getItem();
    }

}
