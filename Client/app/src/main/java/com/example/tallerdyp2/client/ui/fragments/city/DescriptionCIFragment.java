package com.example.tallerdyp2.client.ui.fragments.city;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Proxys.ProxyFragment;
import com.example.tallerdyp2.client.Proxys.ProxyFragmentLocation;
import com.example.tallerdyp2.client.Proxys.ProxyFragmentNoLocation;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.AttractionsAdapter;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;

/**
 * Created by Sebastian on 2/5/2017.
 */

public class DescriptionCIFragment extends Fragment{

    private View rootView;
    private SliderLayout mDemoSlider;
    public City city;
    private String cityName;
    private ProxyFragment proxyLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_description_ci, container, false);

            city = (City) getArguments().
                    getSerializable(getString(R.string.description_at));

            setViewDescription(city);
        }

        return rootView;
    }

    private void setViewDescription(City city) {

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        mDemoSlider.removeAllSliders();
        if(!city.getImagesURL().isEmpty()) {
            for (String url : city.getImagesURL()) {
                DefaultSliderView textSliderView = new DefaultSliderView(getContext());
                textSliderView.image(url);
                mDemoSlider.addSlider(textSliderView);
            }
            if(city.getImagesURL().size() == 1)
                Helper.blockSlide(mDemoSlider);
        }else{
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.image(R.drawable.no_photo);
            mDemoSlider.addSlider(textSliderView);
            Helper.blockSlide(mDemoSlider);
        }

        ElementViewUtils.setText(rootView.findViewById(R.id.description_city),R.id.description_city,city.getDescription());

        this.updateViewAttractions();

    }

    public void updateViewAttractions(){
        LinearLayout list = (LinearLayout) rootView.findViewById(R.id.attractions_list);

        list.removeAllViews();
        if(!city.getAttractions().isEmpty()){
            Helper.updateAtractionsDistanceFromMyLocation(city.getAttractions(), this.proxyLocation.getLatitude(this), this.proxyLocation.getLongitude(this));
            Helper.sortAttractions(city.getAttractions());
            AttractionsAdapter adapter = new AttractionsAdapter(getContext(), city.getAttractions());
            for (int i = 0; i < adapter.getCount(); i++) {
                View view = adapter.getView(i, null, list);
                list.addView(view);
            }
            rootView.findViewById(R.id.attractions_list).setVisibility(View.VISIBLE);
        }else
            rootView.findViewById(R.id.no_attractions).setVisibility(View.VISIBLE);
    }



    @Override
    public void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    public void setProxyLocation() {
        this.proxyLocation = new ProxyFragmentLocation();
    }

    public void setProxyNoLocation() {
        this.proxyLocation = new ProxyFragmentNoLocation();
    }

    private String getCityFromLocation(){
        return AttractionGOApplication.getLocationService().getCityFromLocation();
    }

    public boolean outsideMyCityLocation(){
        return !this.cityName.equals(this.getCityFromLocation());
    }

    public void setCitySelected(String cityName) {
        this.cityName = cityName;
    }
}
