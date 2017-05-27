package com.example.tallerdyp2.client.Services;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 18/5/2017.
 */

public class AnalyticService {

    public void sendVisitAttraction(String attractionId) {
        JSONObject body = new JSONObject();
        try {
            body.put("androidId", SharedPreferencesUtils.getAndroidId());
            body.put("userId", SharedPreferencesUtils.getSplexUserId());
            body.put("socialNetwork", AttractionGOApplication.getSplexService().getSocialNetwork());
            body.put("attraction", attractionId);
            AttractionGOApplication.getVolleyRequestService().sendVisitAttraction(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendAppVisit() {
        JSONObject body = new JSONObject();
        try {
            body.put("androidId", SharedPreferencesUtils.getAndroidId());
            AttractionGOApplication.getVolleyRequestService().sendAppVisit(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendAppVisitLogged() {
        JSONObject body = new JSONObject();
        try {
            body.put("androidId", SharedPreferencesUtils.getAndroidId());
            body.put("userId", SharedPreferencesUtils.getSplexUserId());
            body.put("country", SharedPreferencesUtils.getCountryISO());
            body.put("socialNetwork", AttractionGOApplication.getSplexService().getSocialNetwork());
            AttractionGOApplication.getVolleyRequestService().sendAppVisit(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
