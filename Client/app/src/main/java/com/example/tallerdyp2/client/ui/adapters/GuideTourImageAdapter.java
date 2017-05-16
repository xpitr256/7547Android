package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.List;

/**
 * Created by Sebastian on 12/5/2017.
 */

class GuideTourImageAdapter extends ArrayAdapter<Attraction> {

    private List<Attraction> attractions;

    public GuideTourImageAdapter(Context context, List<Attraction> attractions) {
        super(context, R.layout.guide_tour_item_list, attractions);
        this.attractions = attractions;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.image_guide_tour_item_list, null);
        ElementViewUtils.setImageFromURL(item, R.id.image_view, attractions.get(position).getImagesURL().get(0), getContext());

        return (item);
    }

}