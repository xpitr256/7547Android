package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.ui.fragments.city.DescriptionCIFragment;

/**
 * Created by Sebastian on 3/5/2017.
 */

public class ProxyFragmentNoLocation implements ProxyFragment{
    @Override
    public double getLatitude(DescriptionCIFragment fragment) {
        return fragment.city.getLatitude();
    }

    @Override
    public double getLongitude(DescriptionCIFragment fragment) {
        return fragment.city.getLongitude();
    }
}
