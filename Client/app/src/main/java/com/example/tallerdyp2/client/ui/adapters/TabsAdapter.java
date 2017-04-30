package com.example.tallerdyp2.client.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<Integer> drawablesId;

    public TabsAdapter(FragmentManager fm, List<Fragment> fragments, List<Integer> drawablesId) {
        super(fm);
        this.fragments = fragments;
        this.drawablesId = drawablesId;
    }

    @Override
    public Fragment getItem(int numeroFragment) {
        return fragments.get(numeroFragment);
    }

    public int getDrawableId(int position) {
        return drawablesId.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}