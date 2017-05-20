package com.example.tallerdyp2.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

import java.util.Locale;

/**
 * Created by Sebastian on 31/03/2017.
 */

public class InitialActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "90xCZ8lOFTcguG2NvJ2sxkhAn";
    private static final String TWITTER_SECRET = "jrpVcNV44m4c3VEOZv2DybAAVgA3yjR8ttF6GqVRKWzY1RrCTG";
    private TwitterLoginButton twLoginButton;

    private static final int FB_LOGIN_SUCCESS = -1;
    private static final int FB_LOGIN_NO_SUCCESS = 0;
    private static final int TW_LOGIN = 1;

    private static final int FACEBOOK = 64206;
    private static final int TWITTER = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        if(SharedPreferencesUtils.getLanguage().equals(Constants.EMPTY_STRING)){
            SharedPreferencesUtils.setLanguage(Locale.getDefault().getLanguage());
        }

        SharedPreferencesUtils.setAndroidId(Settings.Secure.getString(AttractionGOApplication.getAppContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));

        AttractionGOApplication.getLanguageService().setLanguage(SharedPreferencesUtils.getLanguage());

        AttractionGOApplication.getAnalyticService().sendAppVisit();

        setContentView(R.layout.activity_initial);

        ElementViewUtils.setImage(findViewById(R.id.image_view),R.id.image_view,R.drawable.logo,getApplicationContext());

        AttractionGOApplication.getFacebookService().checkFacebookLogged();

        AttractionGOApplication.getTwitterService().checkTwitterLogged();

        //FACEBOOK LOGIN
        LoginButton fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);

        fbLoginButton.setReadPermissions("user_friends");
        fbLoginButton.setReadPermissions("public_profile");
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.setReadPermissions("user_birthday");

        AttractionGOApplication.getFacebookService().registerCallbackInButtton(fbLoginButton);

        //TWITTER LOGIN
        twLoginButton = (TwitterLoginButton) findViewById(R.id.tw_login_button);
        AttractionGOApplication.getTwitterService().registerCallbackInButtton(twLoginButton);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //RESULT CODE:
        // -1 FB LOGIN/TWITTER SUCCESS
        // 0 FB LOGIN NO SUCCESS
        // 1 TW LOGIN

        //REQUEST CODE
        //64206 FB
        //140 TW
        this.loadProgressBar();
        switch (requestCode){
            case FACEBOOK:
                if(resultCode < 0)
                    AttractionGOApplication.getFacebookService().onActivityResult(requestCode, resultCode, data);
                break;
            case TWITTER:
//                if(resultCode < 0)
                    twLoginButton.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


    private void loadProgressBar() {
        findViewById(R.id.image_view).setVisibility(View.GONE);
        findViewById(R.id.fb_login_button).setVisibility(View.GONE);
        findViewById(R.id.tw_login_button).setVisibility(View.GONE);
        findViewById(R.id.description).setVisibility(View.GONE);

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }
}
