package com.example.tallerdyp2.client.ui.fragments.attraction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.PointOfInterest;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.PointOfInterestsAdapter;

import java.util.List;

/**
 * Created by Sebastian on 18/4/2017.
 */

public class PointOfInterestFragment extends Fragment {

    private View rootView;
    private ListView pointOfInterestsList;
    private List<PointOfInterest> pointOfInterests;
    private PointOfInterestsAdapter pointOfInterestsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_poi_at, container, false);

        Attraction attraction = (Attraction) getArguments().
                getSerializable(getString(R.string.poi_at));

        setViewPointOfInterest(attraction);

        return rootView;
    }

    private void setViewPointOfInterest(final Attraction attraction) {

        // Instancia del ListView.
        pointOfInterestsList = (ListView) rootView.findViewById(R.id.pois_list);

        pointOfInterests = attraction.getPointOfInterests();

        // Inicializar el adaptador con la fuente de datos.
        pointOfInterestsAdapter = new PointOfInterestsAdapter(getContext(), pointOfInterests);

        //Relacionando la lista con el adaptador
        pointOfInterestsList.setAdapter(pointOfInterestsAdapter);

    }

}