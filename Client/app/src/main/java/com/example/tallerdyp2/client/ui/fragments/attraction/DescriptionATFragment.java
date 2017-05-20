package com.example.tallerdyp2.client.ui.fragments.attraction;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.ms.square.android.expandabletextview.ExpandableTextView;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class DescriptionATFragment extends Fragment implements OnErrorListener {

    private View rootView;
    private EMVideoView emVideoView;
    private SliderLayout mDemoSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_description_at, container, false);

            Attraction attraction = (Attraction) getArguments().
                    getSerializable(getString(R.string.description_at));

            setViewDescription(attraction);
        }

        return rootView;
    }

    private void setViewDescription(Attraction attraction) {

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        if(!attraction.getImagesURL().isEmpty()) {
            for (String url : attraction.getImagesURL()) {
                DefaultSliderView textSliderView = new DefaultSliderView(getContext());
                textSliderView.image(url);
                mDemoSlider.addSlider(textSliderView);
                if(attraction.getImagesURL().size() == 1)
                    Helper.blockSlide(mDemoSlider);

            }
        }else{
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.image(R.drawable.no_photo);
            mDemoSlider.addSlider(textSliderView);
            Helper.blockSlide(mDemoSlider);
        }

        ExpandableTextView expTv1 = (ExpandableTextView) rootView.findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        expTv1.setText(attraction.getDescription());

        this.setupVideoView(rootView, attraction);

    }

    private void setupVideoView(View rootView, Attraction attraction) {
        //INITIALIZE AUDIOVIEW
        emVideoView = (EMVideoView) rootView.findViewById(R.id.audio_view);

        //CHECK IF ATTRACTION HAS AUDIO
        if(attraction.getAudioURL() != null && !attraction.getAudioURL().equals("")){
            emVideoView.setVisibility(View.VISIBLE);
            emVideoView.setOnErrorListener(this);
            emVideoView.setVideoURI(Uri.parse(attraction.getAudioURL()));
            emVideoView.getVideoControls().setCanHide(false);
        }else{
            this.onError();
        }
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

    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }


}
