package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.tallerdyp2.client.Entities.Tour;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.activities.TourActivity;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.List;

/**
 * Created by Sebastian on 4/5/2017.
 */

public class ToursAdapter extends ArrayAdapter<Tour> {

    private List<Tour> tours;

    public ToursAdapter(Context context, List<Tour> tours) {
        super(context, R.layout.tours_item_list, tours);
        this.tours = tours;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.tours_item_list, null);
        ElementViewUtils.setText(item, R.id.name, tours.get(position).getName());
        ElementViewUtils.setText(item, R.id.description, tours.get(position).getDescription());
        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TourActivity.class);
                intent.putExtra("Tour", tours.get(position));
                getContext().startActivity(intent);
            }
        });
        return (item);
    }

}
