package com.example.tallerdyp2.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.List;

/**
 * Created by Sebastian on 15/03/2017.
 */

public class AttractionsAdapter extends ArrayAdapter<String> {

    private List<String> attractions;

    public AttractionsAdapter(Context context, List<String> attractions) {
        super(context, R.layout.attractions_item_list, attractions);
        this.attractions = attractions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.attractions_item_list, null);
        ElementViewUtils.setText(item, R.id.name, attractions.get(position));
        ElementViewUtils.setImage(item, R.id.image_view,"http://i.imgur.com/2IiKOVe.jpg",getContext());

        return (item);
    }

}
