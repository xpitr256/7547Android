package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.ui.activities.CityActivity;

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
}
