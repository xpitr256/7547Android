package com.example.tallerdyp2.client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tallerdyp2.client.Entities.Review;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;

import java.util.List;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class ReviewsAdapter extends ArrayAdapter<Review> {

    private List<Review> reviews;

    public ReviewsAdapter(Context context, List<Review> reviews) {
        super(context, R.layout.reviews_item_list, reviews);
        this.reviews = reviews;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.reviews_item_list, null);
        ElementViewUtils.setText(item, R.id.name, SharedPreferencesUtils.getSplexUserName());
        RatingBar mRatingBar = (RatingBar) item.findViewById(R.id.rating);
        mRatingBar.setRating(reviews.get(position).getPoints());
        ElementViewUtils.setText(item, R.id.comment, "Excelent attraction, i love it!!! Excelent attraction, i love it!!!  Excelent attraction, i love it!!!  Excelent attraction, i love it!!!  Excelent attraction, i love it!!!  Excelent attraction, i love it!!! ");

        return (item);
    }

}
