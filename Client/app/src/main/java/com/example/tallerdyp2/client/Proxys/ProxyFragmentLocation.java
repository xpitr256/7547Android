package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.ui.fragments.city.DescriptionCIFragment;

/**
 * Created by Sebastian on 3/5/2017.
 */

public class ProxyFragmentLocation implements ProxyFragment {
    @Override
    public double getLatitude(DescriptionCIFragment fragment) {
        if(fragment.outsideMyCityLocation())
            return fragment.city.getLatitude();
        return AttractionGOApplication.getLocationService().getLocation().getLatitude();
    }

    @Override
    public double getLongitude(DescriptionCIFragment fragment) {
        if(fragment.outsideMyCityLocation())
            return fragment.city.getLongitude();
        return AttractionGOApplication.getLocationService().getLocation().getLongitude();
    }
}
