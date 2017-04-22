package com.example.tallerdyp2.client.ui.activities;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Parser;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Sebastian on 8/4/2017.
 */

public class MyReviewActivity extends AppCompatActivity implements Callable{

    private Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        attraction = (Attraction) getIntent().getSerializableExtra("Attraction");

        setContentView(R.layout.activity_myreview);

        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);
        Button submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject body = new JSONObject();
                JSONObject review = new JSONObject();
                try {

                    review.put("userName", SharedPreferencesUtils.getSplexUserName());
                    review.put("userId", SharedPreferencesUtils.getFacebookUserId());
                    review.put("userAvatarUrl", "");
                    review.put("comments", ((EditText)findViewById(R.id.comment)).getText());
                    review.put("rating", ratingRatingBar.getRating());
                    body.put("attractionId",attraction.getId());
                    body.put("review",review);

                    AttractionGOApplication.getVolleyRequestService().sendReview(MyReviewActivity.this, body, attraction.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void execute(JSONArray response) {

    }

    @Override
    public void execute(JSONObject response) {
        try {
        Intent intent = new Intent(getApplicationContext(), AttractionActivity.class);
            intent.putExtra("Attraction", Parser.parseAttraction(response));
            finish();
            startActivity(intent);
        } catch (JSONException e) {
            this.error(null);
        }
    }

    @Override
    public void error(VolleyError error) {
        this.showError(getResources().getString(R.string.error_request));
    }

    private void showError(String error) {
        this.hideInformation();
        findViewById(R.id.image_error).setVisibility(View.VISIBLE);
        ElementViewUtils.setText(findViewById(R.id.textErrorRequest), R.id.textErrorRequest, error);
        findViewById(R.id.textErrorRequest).setVisibility(View.VISIBLE);
    }

    private void hideInformation() {
        //hide views
        findViewById(R.id.rating_rating_bar).setVisibility(View.GONE);
        findViewById(R.id.comment).setVisibility(View.GONE);
        findViewById(R.id.submit_button).setVisibility(View.GONE);
    }
}