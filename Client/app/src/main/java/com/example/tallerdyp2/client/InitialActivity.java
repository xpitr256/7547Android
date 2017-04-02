package com.example.tallerdyp2.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Sebastian on 31/03/2017.
 */

public class InitialActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        ElementViewUtils.setImage(findViewById(R.id.image_view),R.id.image_view,R.drawable.logo,getApplicationContext());

        AttractionGOApplication.getFacebookService().checkFacebookLogged();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);



        AttractionGOApplication.getFacebookService().registerCallbackInButtton(loginButton);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AttractionGOApplication.getFacebookService().onActivityResult(requestCode, resultCode, data);
    }
}
