package com.example.tallerdyp2.client.ui.fragments.city;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Entities.Tour;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.ToursAdapter;

import java.util.List;

/**
 * Created by Sebastian on 4/5/2017.
 */

public class TourCIFragment extends Fragment {

    private View rootView;
    private ListView toursList;
    private List<Tour> tours;
    private ToursAdapter toursAdapter;
    private City city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_tour_ci, container, false);

            city = (City) getArguments().
                    getSerializable(getString(R.string.tour_ci));

            setViewTour(city);
        }

        return rootView;
    }

    private void setViewTour(final City city) {

        // Instancia del ListView.
        toursList = (ListView) rootView.findViewById(R.id.tours_list);

        tours = city.getTours();

        // Inicializar el adaptador con la fuente de datos.
        toursAdapter = new ToursAdapter(getContext(), tours);

        //Relacionando la lista con el adaptador
        toursList.setAdapter(toursAdapter);

    }

}