package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;

import com.example.tallerdyp2.client.Entities.PointOfInterest;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.activities.PointOfInterestActivity;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.List;

/**
 * Created by Sebastian on 18/4/2017.
 */

public class PointOfInterestsAdapter extends ArrayAdapter<PointOfInterest> {

    private List<PointOfInterest> pois;

    public PointOfInterestsAdapter(Context context, List<PointOfInterest> pois) {
        super(context, R.layout.pois_item_list, pois);
        this.pois = pois;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.pois_item_list, null);
        ElementViewUtils.setText(item, R.id.name, pois.get(position).getName());
        ElementViewUtils.setImageFromURL(item, R.id.image_view,pois.get(position).getImagesURL().isEmpty() ? null : pois.get(position).getImagesURL().get(0),getContext());
        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PointOfInterestActivity.class);
                intent.putExtra("POI", pois.get(position));
                getContext().startActivity(intent);
            }
        });
        return (item);
    }

}
