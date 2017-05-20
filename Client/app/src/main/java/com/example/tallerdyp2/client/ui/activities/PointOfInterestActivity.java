package com.example.tallerdyp2.client.ui.activities;

import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.example.tallerdyp2.client.Entities.PointOfInterest;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Sebastian on 18/4/2017.
 */

public class PointOfInterestActivity extends AppCompatActivity implements OnErrorListener {
    private PointOfInterest poi;
    private SliderLayout mDemoSlider;
    private EMVideoView emVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_poi);
        poi = (PointOfInterest) getIntent().getSerializableExtra("POI");

        getSupportActionBar().setTitle(poi.getName());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorPrimaryPOI)));

        setViewPager();
    }

    private void setViewPager() {

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        if(!poi.getImagesURL().isEmpty()) {
            for (String url : poi.getImagesURL()) {
                DefaultSliderView textSliderView = new DefaultSliderView(getApplicationContext());
                textSliderView.image(url);
                mDemoSlider.addSlider(textSliderView);
                if(poi.getImagesURL().size() == 1)
                    Helper.blockSlide(mDemoSlider);
            }
        }else{
            DefaultSliderView textSliderView = new DefaultSliderView(getApplicationContext());
            textSliderView.image(R.drawable.no_photo);
            mDemoSlider.addSlider(textSliderView);
            Helper.blockSlide(mDemoSlider);
        }

        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        expTv1.setText(poi.getDescription());

        this.setupVideoView();
    }

    private void setupVideoView() {
        //INITIALIZE AUDIOVIEW
        emVideoView = (EMVideoView) findViewById(R.id.audio_view);

        //CHECK IF ATTRACTION HAS AUDIO
        if(poi.getAudioURL() != null && !poi.getAudioURL().equals("")){
            emVideoView.setVisibility(View.VISIBLE);
            emVideoView.setOnErrorListener(this);
            emVideoView.setVideoURI(Uri.parse(poi.getAudioURL()));
            emVideoView.getVideoControls().setCanHide(false);
        }else{
            this.onError();
        }
    }

    @Override
    public boolean onError() {
        findViewById(R.id.wrong_url).setVisibility(View.VISIBLE);
        findViewById(R.id.audio_view).setVisibility(View.GONE);
        return false;
    }

    @Override
    public void onPause()
    {
        emVideoView.pause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
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
}
