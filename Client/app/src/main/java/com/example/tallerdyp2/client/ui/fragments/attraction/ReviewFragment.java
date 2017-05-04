package com.example.tallerdyp2.client.ui.fragments.attraction;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tallerdyp2.client.Entities.Review;
import com.example.tallerdyp2.client.ui.activities.MyReviewActivity;
import com.example.tallerdyp2.client.ui.adapters.ReviewsAdapter;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class ReviewFragment extends Fragment {

    private View rootView;
    private ListView reviewsList;
    private List<Review> reviews;
    private ReviewsAdapter reviewsAdapter;
    private Attraction attraction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_review_at, container, false);

            attraction = (Attraction) getArguments().
                    getSerializable(getString(R.string.review_at));

            if(!attraction.getReviews().isEmpty())
                setViewReview(attraction);
            else
                setEmptyView();

            setViewAddReview(attraction);
        }


        return rootView;
    }

    private void setViewAddReview(final Attraction attraction) {
        if(!hasMyReview(attraction.getReviews())) {
            FloatingActionButton fabAddReview = (FloatingActionButton) rootView.findViewById(R.id.fab_review);
            fabAddReview.setVisibility(View.VISIBLE);
            fabAddReview.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MyReviewActivity.class);
                    intent.putExtra("Attraction", attraction);
                    startActivity(intent);
                }
            });
        }
    }

    private void setEmptyView() {
        rootView.findViewById(R.id.rating).setVisibility(View.GONE);
        rootView.findViewById(R.id.rating_number).setVisibility(View.GONE);
        rootView.findViewById(R.id.reviews_list).setVisibility(View.GONE);

        rootView.findViewById(R.id.no_reviews).setVisibility(View.VISIBLE);
    }

    private void setViewReview(Attraction attraction) {

        ((RatingBar) rootView.findViewById(R.id.rating)).setRating((float) attraction.getRating());

        ((TextView) rootView.findViewById(R.id.rating_number)).setText(Double.toString(attraction.getRating()));

        // Instancia del ListView.
        reviewsList = (ListView) rootView.findViewById(R.id.reviews_list);

        reviews = attraction.getReviews();

        // Inicializar el adaptador con la fuente de datos.
        reviewsAdapter = new ReviewsAdapter(getContext(), reviews);

        //Relacionando la lista con el adaptador
        reviewsList.setAdapter(reviewsAdapter);
    }

    private boolean hasMyReview(List<Review> reviews) {
        for(Review review : reviews){
            if(review.getUserId().equals(SharedPreferencesUtils.getFacebookUserId()))
                return true;
        }
        return false;
    }

}