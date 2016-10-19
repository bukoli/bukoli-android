package com.mobillium.bukoliandroidsdk.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.ui.FragmentPoint;
import com.mobillium.bukoliandroidsdk.utils.PointCallback;

import java.util.ArrayList;

/**
 * Created by oguzhandongul on 19/10/2016.
 */

public class AdapterPointFragments extends FragmentPagerAdapter {

    ArrayList<BukoliPoint> newsList = new ArrayList<>();
    PointCallback callback;

    public AdapterPointFragments(FragmentManager fragmentManager, ArrayList<BukoliPoint> newsList, PointCallback callback) {
        super(fragmentManager);
        this.newsList = newsList;
        this.callback = callback;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentPoint.newInstance(newsList.get(position),position, callback);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }
}
