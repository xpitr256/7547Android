package com.example.tallerdyp2.client.utils;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Sebastian on 24/3/2017.
 */

public interface Callable {

    void execute(JSONArray response);

    void execute(JSONObject response);

    void error(VolleyError error);
}
