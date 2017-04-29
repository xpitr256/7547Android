package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;

import com.example.tallerdyp2.client.Entities.Review;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

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
        ElementViewUtils.setText(item, R.id.name, reviews.get(position).getUserName());
        ElementViewUtils.setText(item, R.id.date, reviews.get(position).getDate());
        RatingBar mRatingBar = (RatingBar) item.findViewById(R.id.rating);
        mRatingBar.setRating((float)reviews.get(position).getRating());
        ElementViewUtils.setText(item, R.id.comment, reviews.get(position).getComment());

        return (item);
    }

}
