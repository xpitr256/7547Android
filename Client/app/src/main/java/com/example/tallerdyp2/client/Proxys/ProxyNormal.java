package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.CityActivity;

/**
 * Created by Sebastian on 25/3/2017.
 */

public class ProxyNormal implements Proxy{

    @Override
    public void execute(CityActivity activity) {
        activity.getCitiesInfo();
    }

    @Override
    public void getCity(CityActivity activity) {
        activity.getMyCityLocation();
    }

    @Override
    public double getLatitude(CityActivity activity) {
        if(activity.outsideMyCityLocation())
            return activity.city.getLatitude();
        return activity.myLocation.getLatitude();
    }

    @Override
    public double getLongitude(CityActivity activity) {
        if(activity.outsideMyCityLocation())
            return activity.city.getLongitude();
        return activity.myLocation.getLongitude();
    }
}
