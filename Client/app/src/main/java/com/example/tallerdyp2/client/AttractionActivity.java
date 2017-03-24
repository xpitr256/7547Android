package com.example.tallerdyp2.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 23/3/2017.
 */

public class AttractionActivity extends AppCompatActivity {

    private Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);
        attraction = (Attraction) getIntent().getSerializableExtra("Attraction");

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        findViewById(R.id.image_view).setVisibility(View.GONE);
        findViewById(R.id.header_attraction).setVisibility(View.GONE);
        findViewById(R.id.description_attraction).setVisibility(View.GONE);
//        findViewById(R.id.attractions_list).setVisibility(View.GONE);

        this.updateViewAttraction();
    }

    private void updateViewAttraction() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ElementViewUtils.setText(findViewById(R.id.header_attraction),R.id.header_attraction,attraction.getName());
        ElementViewUtils.setImage(findViewById(R.id.image_view),R.id.image_view,attraction.getImageURL(),getApplicationContext());
        ElementViewUtils.setText(findViewById(R.id.description_attraction),R.id.description_attraction,attraction.getDescription());

//        LinearLayout list = (LinearLayout) findViewById(R.id.attractions_list);
//        List<Attraction> attractions = new ArrayList<>();
//        attractions.addAll(city.getAttractions());
//        attractions.addAll(city.getAttractions());
//        attractions.addAll(city.getAttractions());
//        attractions.addAll(city.getAttractions());
//        AttractionsAdapter adapter = new AttractionsAdapter(this, attractions);
//        for (int i = 0; i < adapter.getCount(); i++) {
//            View view = adapter.getView(i, null, list);
//            list.addView(view);
//        }

        findViewById(R.id.image_view).setVisibility(View.VISIBLE);
        findViewById(R.id.header_attraction).setVisibility(View.VISIBLE);
        findViewById(R.id.description_attraction).setVisibility(View.VISIBLE);
//        findViewById(R.id.attractions_list).setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cities:
                Intent intent = new Intent(AttractionActivity.this, CityActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
