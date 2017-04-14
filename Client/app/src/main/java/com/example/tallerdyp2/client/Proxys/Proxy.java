package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.ui.activities.CityActivity;

/**
 * Created by Sebastian on 25/3/2017.
 */

public interface Proxy {
    void execute(CityActivity activity);

    void getCity(CityActivity activity);

    double getLatitude(CityActivity activity);

    double getLongitude(CityActivity activity);
}
