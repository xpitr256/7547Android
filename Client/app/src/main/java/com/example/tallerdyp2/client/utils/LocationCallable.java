package com.example.tallerdyp2.client.utils;

import com.google.android.gms.common.api.Status;

/**
 * Created by Sebastian on 14/4/2017.
 */

public interface LocationCallable {

    void onLocationSuccess();
    void onResolutionRequired(Status status);
    void onLocationChange();
    void onLocationFail();
}
