package com.example.tallerdyp2.client.navigationDrawer;

import com.example.tallerdyp2.client.R;

/**
 * Created by Sebastian on 5/4/2017.
 */

public enum DrawerAction {

    CITIES(R.id.nav_cities, true),
    CLOSE_SESSION(R.id.nav_close_session, false);

    int code;
    public boolean allowsTitle;

    DrawerAction(int code, boolean allowsTitle){
        this.code = code;
        this.allowsTitle = allowsTitle;
    }

    public static DrawerAction findByCode(int code){
        for(DrawerAction a : values()){
            if( a.code == code)
                return a;
        }
        return null;
    }

}
