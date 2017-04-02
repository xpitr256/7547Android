package com.example.tallerdyp2.client.Services;

import android.app.Activity;
import android.content.Intent;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.CityActivity;
import com.example.tallerdyp2.client.InitialActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Sebastian on 1/4/2017.
 */

public class FacebookService {

    CallbackManager callbackManager;
    AccessToken accessToken;

    public FacebookService(){
        callbackManager = CallbackManager.Factory.create();
    }

    private void refreshAccessToken() {
        accessToken = AccessToken.getCurrentAccessToken();
    }

    public void registerCallbackInButtton(LoginButton loginButton) {
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AttractionGOApplication.getSplexService().creatUserSplexWithFacebook(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    public void checkFacebookLogged() {
        this.refreshAccessToken();
        if(accessToken != null && !accessToken.isExpired()){
            Intent intent = new Intent(AttractionGOApplication.getAppContext(), CityActivity.class);
            AttractionGOApplication.getAppContext().startActivity(intent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void logOut() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(AttractionGOApplication.getAppContext(), InitialActivity.class);
        AttractionGOApplication.getAppContext().startActivity(intent);
    }
}
