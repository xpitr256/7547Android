package com.example.tallerdyp2.client.fragments.attraction;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class DescriptionFragment extends Fragment implements OnErrorListener {

    private View rootView;
    private EMVideoView emVideoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_description_at, container, false);

        Attraction attraction = (Attraction) getArguments().
                getSerializable(Constants.DESCRIPTION_AT);

        setViewDescription(attraction);

        return rootView;
    }

    private void setViewDescription(Attraction attraction) {

        ElementViewUtils.setText(rootView.findViewById(R.id.description_attraction),R.id.description_attraction,attraction.getDescription());

        SliderLayout mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        for(String url : attraction.getImagesURL()){
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.image(url);
            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        this.setupVideoView(rootView, attraction);

    }

    private void setupVideoView(View rootView, Attraction attraction) {
        emVideoView = (EMVideoView) rootView.findViewById(R.id.audio_view);
        emVideoView.setVisibility(View.VISIBLE);
        emVideoView.setOnErrorListener(this);
        emVideoView.setVideoURI(Uri.parse(attraction.getAudioURL()));
        emVideoView.getVideoControls().setCanHide(false);
    }

    @Override
    public boolean onError() {
        rootView.findViewById(R.id.wrong_url).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.audio_view).setVisibility(View.GONE);
        return false;
    }

    @Override
    public void onPause()
    {
        emVideoView.pause();
        super.onPause();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (this.isVisible()) {
            if (!isVisibleToUser) {
                emVideoView.pause();
            }
        }
    }


}