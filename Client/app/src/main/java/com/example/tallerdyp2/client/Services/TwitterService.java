package com.example.tallerdyp2.client.Services;

import android.content.Intent;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.ui.activities.CityActivity;
import com.example.tallerdyp2.client.ui.activities.InitialActivity;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by Sebastian on 16/5/2017.
 */

public class TwitterService {

    public void registerCallbackInButtton(TwitterLoginButton loginButton) {
        // Callback registration
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                AttractionGOApplication.getSplexService().creatUserSplexWithTwitter(session.getAuthToken().token, session.getAuthToken().secret);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }



    public void checkTwitterLogged() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if(twitterSession != null){
            AttractionGOApplication.getSplexService().logInTwitter();
        }
    }

    public void logOut() {
//        CookieSyncManager.createInstance(AttractionGOApplication.getAppContext());
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.removeSessionCookie();
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();

        AttractionGOApplication.getSplexService().cleanSession();

        Intent intent = new Intent(AttractionGOApplication.getAppContext(), InitialActivity.class);
        AttractionGOApplication.getAppContext().startActivity(intent);
    }
}
