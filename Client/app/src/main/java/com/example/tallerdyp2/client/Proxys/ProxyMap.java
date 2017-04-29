package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.ui.activities.MapsActivity;

/**
 * Created by Sebastian on 16/4/2017.
 */

public interface ProxyMap {
    double getLatitude(MapsActivity activity);

    double getLongitude(MapsActivity activity);
}
