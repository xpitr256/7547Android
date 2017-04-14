package com.example.tallerdyp2.client.navigationDrawer;

/**
 * Created by Sebastian on 5/4/2017.
 */

public interface Transactional {
    void startActivity(Class activity);
    void closeSession();
    void changeLanguage();
}
