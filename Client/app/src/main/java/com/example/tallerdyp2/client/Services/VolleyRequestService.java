package com.example.tallerdyp2.client.Services;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.example.tallerdyp2.client.utils.SplexCallable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept-Language",SharedPreferencesUtils.getLanguage());

                return headers;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }

    public void getAttraction(final Callable call, String attractionId){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, Constants.IP + "/attraction/"+attractionId,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                call.execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                call.error(error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept-Language",SharedPreferencesUtils.getLanguage());

                return headers;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }


    public void createSplexUser(final SplexCallable call, final JSONObject data, String provider) {
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://api.splex.rocks/signup/"+provider,
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                call.executeLogIn(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                call.error(error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+Constants.SPLEX_APP_TOKEN);

                return headers;
            }
        }
        ;
        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }



    public void logSplexUser(final SplexCallable call, final JSONObject data, String provider) {
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://api.splex.rocks/login/"+provider,
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                call.executeLogIn(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                call.error(error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+Constants.SPLEX_APP_TOKEN);

                return headers;
            }
        }
                ;
        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }

    public void retrieveSplexUserData(final SplexCallable call) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, "https://api.splex.rocks/social-accounts/",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                call.executeUserData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                call.error(error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+ SharedPreferencesUtils.getTokenSplex());

                return headers;
            }
        };

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }

    public void sendReview(final Callable call, JSONObject data, final String attractionId) {
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Constants.IP + "/review",
                data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AttractionGOApplication.getVolleyRequestService().getAttraction(call, attractionId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                call.error(error);
            }
        });
        // Add the request to the RequestQueue.
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }

    public void getPaths(List<String> paths, final Callable call) {

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                paths.get(0), null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                call.execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding request to request queue
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }

    public void getPath(String url, final Callable call) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                call.execute(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding request to request queue
        Volley.newRequestQueue(AttractionGOApplication.getAppContext()).add(jsObjRequest);
    }
}