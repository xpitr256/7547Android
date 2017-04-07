package com.example.tallerdyp2.client;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<CharSequence> titles;

    public TabsAdapter(FragmentManager fm, List<Fragment> fragments, List<CharSequence> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int numeroFragment) {
        return fragments.get(numeroFragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

}