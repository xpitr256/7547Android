package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.ui.activities.TourActivity;

/**
 * Created by Sebastian on 16/4/2017.
 */

public class ProxyMapNormal implements ProxyMap {
    @Override
    public double getLatitude(TourActivity activity) {
        if(activity.outsideMyTourLocation())
            return activity.getLatitude();
        return AttractionGOApplication.getLocationService().getLocation().getLatitude();
    }

    @Override
    public double getLongitude(TourActivity activity) {
        if(activity.outsideMyTourLocation())
            return activity.getLongitude();
        return AttractionGOApplication.getLocationService().getLocation().getLongitude();
    }
}
