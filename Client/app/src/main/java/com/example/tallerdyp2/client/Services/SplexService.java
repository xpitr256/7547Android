package com.example.tallerdyp2.client.Services;

import android.content.Intent;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.ui.activities.CityActivity;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.example.tallerdyp2.client.utils.SplexCallable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sebastian on 1/4/2017.
 */

public class SplexService implements SplexCallable {

    String token;
    String secretToken;
    String provider;

    public void creatUserSplexWithFacebook(String token) {
        this.token = token;
        this.provider = "facebook";
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

    private void logSplexWithSocialNetwork() {
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("accessToken", token);
            if(!this.loggedInFacebook())
                body.put("accessTokenSecret", secretToken);
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
                if(data.getJSONObject(i).getString("provider").equals("FACEBOOK") || data.getJSONObject(i).getString("provider").equals("TWITTER")){
                    SharedPreferencesUtils.setSplexUserName(data.getJSONObject(i).getJSONObject("data").getString("name"));
                    SharedPreferencesUtils.setSplexUserId(data.getJSONObject(i).getJSONObject("data").getString("user_id"));
                }
            }

        } catch (JSONException e) {
            SharedPreferencesUtils.setSplexUserName(Constants.EMPTY_STRING);
            SharedPreferencesUtils.setSplexUserId(Constants.EMPTY_STRING);
        }

        logIn();
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
                this.logSplexWithSocialNetwork();
                break;
            case 500 :
                Toast.makeText(AttractionGOApplication.getAppContext(), "internal server error", Toast.LENGTH_SHORT);
                break;
            default :
                break;
        }
    }

    public void cleanSession() {
        SharedPreferencesUtils.setSplexUserName(Constants.EMPTY_STRING);
        SharedPreferencesUtils.setSplexUserId(Constants.EMPTY_STRING);
    }

    public void creatUserSplexWithTwitter(String token, String secretToken) {
        this.token = token;
        this.secretToken = secretToken;
        this.provider = "twitter";
        JSONObject body = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            body.put("accessToken", token);
            body.put("accessTokenSecret", secretToken);
            data.put("data", body);
            AttractionGOApplication.getVolleyRequestService().createSplexUser(this,data,provider);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean loggedInFacebook(){
        return this.provider.equals("facebook");
    }

    public void logOut() {
        if(this.loggedInFacebook())
            AttractionGOApplication.getFacebookService().logOut();
        else
            AttractionGOApplication.getTwitterService().logOut();
    }

    public void logInFacebook() {
        this.provider = "facebook";

        this.logIn();
    }

    public void logInTwitter() {
        this.provider = "twitter";

        this.logIn();
    }

    private void logIn() {
        AttractionGOApplication.getAnalyticService().sendAppVisitLogged();

        Intent intent = new Intent(AttractionGOApplication.getAppContext(), CityActivity.class);
        AttractionGOApplication.getAppContext().startActivity(intent);
    }

    public String getSocialNetwork(){
        if(this.loggedInFacebook())
            return Constants.FACEBOOK;
        else
            return Constants.TWITTER;
    }
}
