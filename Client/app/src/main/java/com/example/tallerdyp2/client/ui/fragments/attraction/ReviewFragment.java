package com.example.tallerdyp2.client.ui.fragments.attraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.tallerdyp2.client.Entities.Review;
import com.example.tallerdyp2.client.ui.adapters.ReviewsAdapter;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;

import java.util.List;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class ReviewFragment extends Fragment {

    private View rootView;
    private ListView reviewsList;
    private List<Review> reviews;
    private ReviewsAdapter reviewsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_review_at, container, false);

        Attraction attraction = (Attraction) getArguments().
                getSerializable(getString(R.string.review_at));

        setViewReview(attraction);

        return rootView;
    }

    private void setViewReview(Attraction attraction) {

        ((RatingBar) rootView.findViewById(R.id.rating)).setRating((float) attraction.getRating());

        // Instancia del ListView.
        reviewsList = (ListView) rootView.findViewById(R.id.reviews_list);

        reviews = attraction.getReviews();

        // Inicializar el adaptador con la fuente de datos.
        reviewsAdapter = new ReviewsAdapter(getContext(), reviews);

        //Relacionando la lista con el adaptador
        reviewsList.setAdapter(reviewsAdapter);

    }

}