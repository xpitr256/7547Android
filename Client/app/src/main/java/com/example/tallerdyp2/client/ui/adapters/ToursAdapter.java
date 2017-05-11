package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.Tour;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.activities.TourActivity;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

/**
 * Created by Sebastian on 4/5/2017.
 */

public class ToursAdapter extends ArrayAdapter<Tour> {

    private List<Tour> tours;

    public ToursAdapter(Context context, List<Tour> tours) {
        super(context, R.layout.tours_item_list, tours);
        this.tours = tours;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.tours_item_list, null);

        SliderLayout mDemoSlider = (SliderLayout) item.findViewById(R.id.slider);
        if(!this.tours.get(position).getAttractions().isEmpty()) {
            for (Attraction attraction : this.tours.get(position).getAttractions()) {
                DefaultSliderView textSliderView = new DefaultSliderView(getContext());
                textSliderView.image(attraction.getImagesURL().get(0));
                mDemoSlider.addSlider(textSliderView);
                if(this.tours.get(position).getAttractions().size() == 1)
                    Helper.blockSlide(mDemoSlider);
            }
        }else{
            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
            textSliderView.image(R.drawable.no_photo);
            mDemoSlider.addSlider(textSliderView);
            Helper.blockSlide(mDemoSlider);
        }

        ExpandableTextView expTv1 = (ExpandableTextView) item.findViewById(R.id.expand_text_view)
                .findViewById(R.id.expand_text_view);

        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(tours.get(position).getDescription());

//        ElementViewUtils.setText(item, R.id.name, tours.get(position).getName());
//        ElementViewUtils.setText(item, R.id.description, tours.get(position).getDescription());
        item.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TourActivity.class);
                intent.putExtra("Tour", tours.get(position));
                getContext().startActivity(intent);
            }
        });
        return (item);
    }

}
