package com.example.tallerdyp2.client.ui.fragments.attraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.Tour;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.ToursAdapter;

import java.util.List;

/**
 * Created by Sebastian on 13/5/2017.
 */

public class TourATFragment extends Fragment {

    private View rootView;
    private ListView toursList;
    private List<Tour> tours;
    private ToursAdapter toursAdapter;
    private Attraction attraction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_tour_ci, container, false);

            attraction = (Attraction) getArguments().
                    getSerializable(getString(R.string.tour_ci));

            setViewTour(attraction);
        }

        return rootView;
    }

    private void setViewTour(final Attraction attraction) {

        // Instancia del ListView.
        toursList = (ListView) rootView.findViewById(R.id.tours_list);

        tours = attraction.getToursIBelongTo();

        // Inicializar el adaptador con la fuente de datos.
        toursAdapter = new ToursAdapter(getContext(), tours);

        //Relacionando la lista con el adaptador
        toursList.setAdapter(toursAdapter);

    }

}