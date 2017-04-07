package com.example.tallerdyp2.client.utils;

import android.content.SharedPreferences;

import com.example.tallerdyp2.client.AttractionGOApplication;

/**
 * Created by Sebastian on 1/4/2017.
 */

public class SharedPreferencesUtils {

    private static SharedPreferences prefs = AttractionGOApplication.getAppContext().
            getSharedPreferences(Constants.PREFERENCES, AttractionGOApplication.getAppContext().MODE_PRIVATE);


    public static String getTokenSplex() {
        String token = prefs.getString(Constants.TOKEN_SPLEX_SESSION, Constants.EMPTY_STRING);

        return token;
    }

    public static void setTokenSplex(String token) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.TOKEN_SPLEX_SESSION, token);

        editor.commit();
    }

    public static String getFacebookUserId() {
        String token = prefs.getString(Constants.FB_USER_ID, Constants.EMPTY_STRING);

        return token;
    }

    public static void setFacebookUserId(String userId) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.FB_USER_ID, userId);

        editor.commit();
    }

    public static String getSplexUserName() {
        String token = prefs.getString(Constants.SPLEX_USER_NAME, Constants.EMPTY_STRING);

        return token;
    }

    public static void setSplexUserName(String userId) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.SPLEX_USER_NAME, userId);

        editor.commit();
    }
}