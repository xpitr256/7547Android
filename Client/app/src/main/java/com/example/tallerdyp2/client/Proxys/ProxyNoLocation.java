package com.example.tallerdyp2.client.Proxys;

import android.content.Intent;

import com.example.tallerdyp2.client.CitiesActivity;
import com.example.tallerdyp2.client.CityActivity;

/**
 * Created by Sebastian on 25/3/2017.
 */

public class ProxyNoLocation implements Proxy{

    @Override
    public void execute(CityActivity activity) {
        if(activity.citySelected())
            activity.getCitiesInfo();
        else{
            Intent intent = new Intent(activity, CitiesActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    public void getCity(CityActivity activity) {
        activity.getMyCity();
    }

    @Override
    public double getLatitude(CityActivity activity) {
        return activity.city.getLatitude();
    }

    @Override
    public double getLongitude(CityActivity activity) {
        return activity.city.getLongitude();
    }
}
