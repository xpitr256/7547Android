package com.example.tallerdyp2.client.Proxys;

import com.example.tallerdyp2.client.ui.fragments.city.DescriptionCIFragment;

/**
 * Created by Sebastian on 3/5/2017.
 */

public interface ProxyFragment {
    double getLatitude(DescriptionCIFragment fragment);

    double getLongitude(DescriptionCIFragment fragment);
}
