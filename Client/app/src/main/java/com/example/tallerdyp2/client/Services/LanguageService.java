package com.example.tallerdyp2.client.Services;

import android.content.res.Configuration;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;

import java.util.Locale;

/**
 * Created by Sebastian on 14/4/2017.
 */

public class LanguageService {

    private static String[] idiomsCode = {
            Constants.EN,
            Constants.ES,
            Constants.PT
    };

    public String[] getIdiomsName() {
        return new String[]{
                AttractionGOApplication.getAppContext().getString(R.string.english),
                AttractionGOApplication.getAppContext().getString(R.string.spanish),
                AttractionGOApplication.getAppContext().getString(R.string.portugues)
        };
    }

    public String getIdiomCode(int position){
        return idiomsCode[position];
    }


    public void setLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        AttractionGOApplication.getAppBaseContext().getResources().updateConfiguration(config, AttractionGOApplication.getAppBaseContext().getResources().getDisplayMetrics());

        SharedPreferencesUtils.setLanguage(language);
    }

    public int getIdiomSelected() {
        for(int i = 0; i < idiomsCode.length; ++i){
            if(idiomsCode[i].equals(SharedPreferencesUtils.getLanguage()))
                return i;
        }
        return 0;
    }
}
