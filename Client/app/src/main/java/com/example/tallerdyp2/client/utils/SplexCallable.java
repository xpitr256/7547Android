package com.example.tallerdyp2.client.utils;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Sebastian on 7/4/2017.
 */

public interface SplexCallable {
    void executeLogIn(JSONObject response);

    void executeUserData(JSONObject response);

    void error(VolleyError error);
}
