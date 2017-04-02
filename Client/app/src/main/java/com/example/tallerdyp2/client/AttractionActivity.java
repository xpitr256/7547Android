package com.example.tallerdyp2.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

/**
 * Created by Sebastian on 23/3/2017.
 */

public class AttractionActivity extends AppCompatActivity {

    private Attraction attraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_attraction);
        attraction = (Attraction) getIntent().getSerializableExtra("Attraction");

        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        findViewById(R.id.slider).setVisibility(View.GONE);
        findViewById(R.id.header_attraction).setVisibility(View.GONE);
        findViewById(R.id.description_attraction).setVisibility(View.GONE);

        this.updateViewAttraction();
    }

    private void updateViewAttraction() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ElementViewUtils.setText(findViewById(R.id.header_attraction),R.id.header_attraction,attraction.getName());
        ElementViewUtils.setText(findViewById(R.id.description_attraction),R.id.description_attraction,attraction.getDescription());

        SliderLayout mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        for(String url : attraction.getImagesURL()){
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView.image(url);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        findViewById(R.id.slider).setVisibility(View.VISIBLE);
        findViewById(R.id.header_attraction).setVisibility(View.VISIBLE);
        findViewById(R.id.description_attraction).setVisibility(View.VISIBLE);

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
                Intent intent = new Intent(AttractionActivity.this, CitiesActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
