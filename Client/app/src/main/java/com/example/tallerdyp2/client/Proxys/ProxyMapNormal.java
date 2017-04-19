package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.ui.activities.MapsActivity;

/**
 * Created by Sebastian on 16/4/2017.
 */

public class ProxyMapNormal implements ProxyMap {
    @Override
    public double getLatitude(MapsActivity activity) {
        if(activity.outsideMyCityLocation())
            return activity.getLatitude();
        return AttractionGOApplication.getLocationService().getLocation().getLatitude();
    }

    @Override
    public double getLongitude(MapsActivity activity) {
        if(activity.outsideMyCityLocation())
            return activity.getLongitude();
        return AttractionGOApplication.getLocationService().getLocation().getLongitude();
    }
}