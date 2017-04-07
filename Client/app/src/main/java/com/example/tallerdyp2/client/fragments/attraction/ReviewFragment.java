package com.example.tallerdyp2.client.fragments.attraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.example.tallerdyp2.client.Entities.Review;
import com.example.tallerdyp2.client.ReviewsAdapter;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class ReviewFragment extends Fragment {

    private View rootView;
    private ListView reviewsList;
    private ArrayList<Review> reviews;
    private ReviewsAdapter reviewsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_at, container, false);

        Attraction attraction = (Attraction) getArguments().
                getSerializable(Constants.REVIEW_AT);

        setViewReview(attraction);

        return rootView;
    }

    private void setViewReview(Attraction attraction) {
        
        // Instancia del ListView.
        reviewsList = (ListView) rootView.findViewById(R.id.reviews_list);

        reviews = new ArrayList<>();
        Review myReview = new Review("seba", "muy bueno", 4);

        reviews.add(myReview);
        reviews.add(myReview);
        reviews.add(myReview);

        // Inicializar el adaptador con la fuente de datos.
        reviewsAdapter = new ReviewsAdapter(getContext(), reviews);

        //Relacionando la lista con el adaptador
        reviewsList.setAdapter(reviewsAdapter);

    }

}