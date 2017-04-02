package com.example.tallerdyp2.client;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.tallerdyp2.client.Services.FacebookService;
import com.example.tallerdyp2.client.Services.SplexService;
import com.example.tallerdyp2.client.Services.VolleyRequestService;

/**
 * Created by Sebastian on 24/3/2017.
 */

public class AttractionGOApplication extends MultiDexApplication {

    private static Context context;
    private static VolleyRequestService volleyRequestService;
    private static FacebookService facebookService;
    private static SplexService splexService;


    @Override
    public void onCreate() {

        super.onCreate();
        AttractionGOApplication.context = getApplicationContext();

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

    public static SplexService getSplexService(){

        if(splexService == null)
            splexService = new SplexService();

        return splexService;
    }
}
