package com.example.tallerdyp2.client.builders;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class TabFragmentBuilder<T extends Serializable> {

    Fragment fragment;
    String name;
    T entity;

    public TabFragmentBuilder(Fragment fragment, String name, T entity) {
        this.fragment = fragment;
        this.name = name;
        this.entity = entity;
    }

    public Fragment buildFragment(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(name, entity);
        fragment.setArguments(bundle);
        return fragment;
    }

}
