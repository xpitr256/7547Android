package com.example.tallerdyp2.client.Services;

import android.content.Intent;
import android.telecom.Call;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.CityActivity;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 1/4/2017.
 */

public class SplexService implements Callable{

    String token;
    String provider = "facebook";

    public void creatUserSplexWithFacebook(String token) {
        this.token = token;
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("accessToken", token);
            data.put("data", body);
            AttractionGOApplication.getVolleyRequestService().createSplexUser(this,data,provider);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void logSplexWithFacebook() {
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("accessToken", token);
            data.put("data", body);
            AttractionGOApplication.getVolleyRequestService().logSplexUser(this,data,provider);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(JSONArray response) {

    }

    @Override
    public void execute(JSONObject response) {
        try {
            SharedPreferencesUtils.setTokenSplex(response.getString("splexUserToken"));
        } catch (JSONException e) {
            SharedPreferencesUtils.setTokenSplex(Constants.EMPTY_STRING);
        }
        Intent intent = new Intent(AttractionGOApplication.getAppContext(), CityActivity.class);
        AttractionGOApplication.getAppContext().startActivity(intent);
    }

    @Override
    public void error(VolleyError error) {

        switch (error.networkResponse.statusCode){
            case 400 :
                Toast.makeText(AttractionGOApplication.getAppContext(), "bad request", Toast.LENGTH_SHORT);
                break;
            case 422 :
                this.logSplexWithFacebook();
                break;
            case 500 :
                Toast.makeText(AttractionGOApplication.getAppContext(), "internal server error", Toast.LENGTH_SHORT);
                break;
            default :
                break;
        }
    }
}
