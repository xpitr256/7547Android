package com.example.tallerdyp2.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Sebastian on 31/03/2017.
 */

public class InitialActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        AttractionGOApplication.getLanguageService().setLanguage(SharedPreferencesUtils.getLanguage());

        setContentView(R.layout.activity_initial);

        ElementViewUtils.setImage(findViewById(R.id.image_view),R.id.image_view,R.drawable.logo,getApplicationContext());

        AttractionGOApplication.getFacebookService().checkFacebookLogged();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");

        AttractionGOApplication.getFacebookService().registerCallbackInButtton(loginButton);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode < 0){
            this.loadProgressBar();
            AttractionGOApplication.getFacebookService().onActivityResult(requestCode, resultCode, data);
        }
    }

    private void loadProgressBar() {
        findViewById(R.id.image_view).setVisibility(View.GONE);
        findViewById(R.id.header_welcome).setVisibility(View.GONE);
        findViewById(R.id.login_button).setVisibility(View.GONE);
        findViewById(R.id.description).setVisibility(View.GONE);

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }
}
