package com.example.tallerdyp2.client;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.tallerdyp2.client.Services.AnalyticService;
import com.example.tallerdyp2.client.Services.FacebookService;
import com.example.tallerdyp2.client.Services.LanguageService;
import com.example.tallerdyp2.client.Services.LocationService;
import com.example.tallerdyp2.client.Services.SplexService;
import com.example.tallerdyp2.client.Services.TwitterService;
import com.example.tallerdyp2.client.Services.VolleyRequestService;
import com.facebook.internal.AnalyticsEvents;

/**
 * Created by Sebastian on 24/3/2017.
 */

public class AttractionGOApplication extends MultiDexApplication {

    private static Context context;
    private static VolleyRequestService volleyRequestService;
    private static FacebookService facebookService;
    private static TwitterService twitterService;
    private static SplexService splexService;
    private static Context baseContext;
    private static LanguageService laguageService;
    private static LocationService locationService;
    private static AnalyticService analyticService;

    @Override
    public void onCreate() {

        super.onCreate();
        AttractionGOApplication.context = getApplicationContext();
        AttractionGOApplication.baseContext = getBaseContext();

//        //Override fonts
//        CustomFonts.setDefaultFont(this, "DEFAULT", "fonts/Roboto-Regular.ttf");
//        CustomFonts.setDefaultFont(this, "DEFAULT_BOLD", "fonts/Roboto-Bold.ttf");
//        CustomFonts.setDefaultFont(this, "MONOSPACE", "fonts/Roboto-Regular.ttf");
//        CustomFonts.setDefaultFont(this, "SERIF", "fonts/Roboto-Regular.ttf");
//        CustomFonts.setDefaultFont(this, "SANS_SERIF", "fonts/Roboto-Regular.ttf");

    }

    public static Context getAppContext() {
        return AttractionGOApplication.context;
    }

    public static Context getAppBaseContext() {
        return AttractionGOApplication.baseContext;
    }

    public static VolleyRequestService getVolleyRequestService(){

        if(volleyRequestService == null)
            volleyRequestService = new VolleyRequestService();

        return volleyRequestService;
    }


    public static FacebookService getFacebookService(){

        if(facebookService == null)
            facebookService = new FacebookService();

        return facebookService;
    }

    public static TwitterService getTwitterService(){

        if(twitterService == null)
            twitterService = new TwitterService();

        return twitterService;
    }

    public static SplexService getSplexService(){

        if(splexService == null)
            splexService = new SplexService();

        return splexService;
    }

    public static LanguageService getLanguageService(){

        if(laguageService == null)
            laguageService = new LanguageService();

        return laguageService;
    }

    public static LocationService getLocationService(){

        if(locationService == null)
            locationService = new LocationService();

        return locationService;
    }

    public static AnalyticService getAnalyticService(){

        if(analyticService == null)
            analyticService = new AnalyticService();

        return analyticService;
    }

}
