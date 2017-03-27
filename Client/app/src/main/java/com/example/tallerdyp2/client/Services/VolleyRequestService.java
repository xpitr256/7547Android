package com.example.tallerdyp2.client.Services;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;

import org.json.JSONArray;

/**
 * Created by Sebastian on 24/3/2017.
 */

public class VolleyRequestService {

    public void getCities(final Callable call){

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, Constants.IP + "/city",
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                call.execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                call.error(error);
            }
        });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }


}