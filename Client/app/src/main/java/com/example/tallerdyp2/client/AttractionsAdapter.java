package com.example.tallerdyp2.client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;

import java.util.List;

/**
 * Created by Sebastian on 15/03/2017.
 */

public class AttractionsAdapter extends ArrayAdapter<Attraction> {

    private List<Attraction> attractions;

    public AttractionsAdapter(Context context, List<Attraction> attractions) {
        super(context, R.layout.attractions_item_list, attractions);
        this.attractions = attractions;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.attractions_item_list, null);
        ElementViewUtils.setText(item, R.id.name, attractions.get(position).getName());
        ElementViewUtils.setText(item, R.id.distance, getContext().getString(R.string.distance_attraction)+" "+Helper.formatDistance(attractions.get(position).getDistance()));
        ElementViewUtils.setImageFromURL(item, R.id.image_view,attractions.get(position).getImageURL(),getContext());
        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AttractionActivity.class);
                intent.putExtra("Attraction", attractions.get(position));
                getContext().startActivity(intent);
            }
        });

        return (item);
    }

}
