package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.activities.AttractionActivity;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by Sebastian on 4/5/2017.
 */

public class MarkerPopupAdapter implements GoogleMap.InfoWindowAdapter {
    LayoutInflater inflater=null;
    private View popup;
    private Marker lastMarker;
    private Map<Object, Attraction> markersAttractionMap;
    private Attraction attraction;

    public MarkerPopupAdapter(LayoutInflater inflater, Map<Object, Attraction> markersAttractionMap) {
        this.inflater=inflater;
        this.markersAttractionMap = markersAttractionMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return(null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (popup == null) {
            popup=inflater.inflate(R.layout.popup, null);
        }
        if (lastMarker == null || !lastMarker.getId().equals(marker.getId())) {
            lastMarker = marker;

            attraction = markersAttractionMap.get(marker.getTag());

            ElementViewUtils.setText(popup, R.id.name, attraction.getName());
            ElementViewUtils.setText(popup, R.id.description, attraction.getDescription());
            RatingBar mRatingBar = (RatingBar) popup.findViewById(R.id.rating);
            mRatingBar.setRating((float)attraction.getRating());

            ImageView icon = (ImageView) popup.findViewById(R.id.icon);

//        if (image == null) {
//            icon.setVisibility(View.GONE);
//        }
//        else {
            Picasso.with(AttractionGOApplication.getAppContext()).load(attraction.getImagesURL().get(0)).resize(160, 160)
                    .centerCrop().noFade()
                    .placeholder(R.drawable.progress_animation)
                    .into(icon, new MarkerCallback(marker));


//        }
        }
        return popup;
    }

    static class MarkerCallback implements Callback {
        Marker marker=null;

        MarkerCallback(Marker marker) {
            this.marker=marker;
        }

        @Override
        public void onError() {
        }

        @Override
        public void onSuccess() {
            if (marker != null && marker.isInfoWindowShown()) {
                marker.showInfoWindow();
            }
        }
    }
}