package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.ui.activities.TourActivity;

/**
 * Created by Sebastian on 16/4/2017.
 */

public class ProxyMapNoLocation implements ProxyMap {
    @Override
    public double getLatitude(TourActivity activity) {
        return activity.getLatitude();
    }

    @Override
    public double getLongitude(TourActivity activity) {
        return activity.getLongitude();
    }
}
