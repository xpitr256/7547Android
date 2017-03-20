package com.example.tallerdyp2.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.List;

/**
 * Created by Sebastian on 14/3/2017.
 */

public class CitiesAdapter extends ArrayAdapter<City> {

    private List<City> cities;

    public CitiesAdapter(Context context, List<City> cities) {
        super(context, R.layout.cities_item_list, cities);
        this.cities = cities;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.cities_item_list, null);
        ElementViewUtils.setText(item, R.id.name, cities.get(position).getName());
        return (item);
    }

}
