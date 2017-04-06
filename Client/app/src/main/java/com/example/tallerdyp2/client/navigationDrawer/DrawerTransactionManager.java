package com.example.tallerdyp2.client.navigationDrawer;

/**
 * Created by Sebastian on 5/4/2017.
 */

import com.example.tallerdyp2.client.CitiesActivity;

import java.util.HashMap;
import java.util.Map;

public class DrawerTransactionManager {

    private static DrawerTransactionManager instance;

    private Map<DrawerAction, TransactionManager> transactionMap;

    private DrawerTransactionManager() {

        transactionMap = new HashMap<>();
        transactionMap.put(DrawerAction.CITIES, new TransactionManager() {
            public void beginTransaction(Transactional transactional) {
                transactional.startActivity(CitiesActivity.class);
            }
        });
        transactionMap.put(DrawerAction.CLOSE_SESSION, new TransactionManager() {
            public void beginTransaction(Transactional transactional) {
                transactional.closeSession();
            }
        });
    }

    public static DrawerTransactionManager getInstance() {
        if (instance == null)
            instance = new DrawerTransactionManager();

        return instance;
    }

    public void startTransaction(DrawerAction action, Transactional transactional) {
        if (transactionMap.containsKey(action))
            transactionMap.get(action).beginTransaction(transactional);
    }

    private interface TransactionManager {
        void beginTransaction(Transactional transactional);
    }

}
