package com.example.tallerdyp2.client.Services;

import android.content.Intent;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.CityActivity;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.example.tallerdyp2.client.utils.SplexCallable;
import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 1/4/2017.
 */

public class SplexService implements SplexCallable {

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
    public void executeUserData(JSONObject response) {
        try {
            JSONArray data = response.getJSONObject("data").getJSONArray("socialAccounts");
            for(int i  = 0; i < data.length(); i++) {
                if(data.getJSONObject(i).getString("provider").equals("FACEBOOK")){
                    SharedPreferencesUtils.setSplexUserName(data.getJSONObject(i).getJSONObject("data").getString("name"));
                    SharedPreferencesUtils.setFacebookUserId(data.getJSONObject(i).getJSONObject("data").getString("user_id"));
                }
            }

        } catch (JSONException e) {
            SharedPreferencesUtils.setSplexUserName(Constants.EMPTY_STRING);
            SharedPreferencesUtils.setFacebookUserId(Constants.EMPTY_STRING);
        }
        Intent intent = new Intent(AttractionGOApplication.getAppContext(), CityActivity.class);
        AttractionGOApplication.getAppContext().startActivity(intent);
    }

    @Override
    public void executeLogIn(JSONObject response) {
        try {
            SharedPreferencesUtils.setTokenSplex(response.getJSONObject("data").getString("splexUserToken"));
        } catch (JSONException e) {
            SharedPreferencesUtils.setTokenSplex(Constants.EMPTY_STRING);
        }
        AttractionGOApplication.getVolleyRequestService().retrieveSplexUserData(this);
    }

    @Override
    public void error(VolleyError error) {

        switch (error.networkResponse.statusCode){
            case 400 :
                Toast.makeText(AttractionGOApplication.getAppContext(), "bad request", Toast.LENGTH_SHORT);
                break;
            case 401 :
                Toast.makeText(AttractionGOApplication.getAppContext(), "bad token", Toast.LENGTH_SHORT);
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
