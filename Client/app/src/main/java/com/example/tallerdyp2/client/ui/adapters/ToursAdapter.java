package com.example.tallerdyp2.client.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.andexert.expandablelayout.library.ExpandableLayout;
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
    private LinearLayout imagesList;
    private LinearLayout listPlaces;
    private LinearLayout scroll;
    private ImageView location;
    private GuideTourAdapter adapter;
    private GuideTourImageAdapter adapterImages;
    private View view;

    public ToursAdapter(Context context, List<Tour> tours) {
        super(context, R.layout.tours_item_list, tours);
        this.tours = tours;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.tours_item_list, null);

//        SliderLayout mDemoSlider = (SliderLayout) item.findViewById(R.id.slider);
//        if(!this.tours.get(position).getAttractions().isEmpty()) {
//            for (Attraction attraction : this.tours.get(position).getAttractions()) {
//                DefaultSliderView textSliderView = new DefaultSliderView(getContext());
//                textSliderView.image(attraction.getImagesURL().get(0));
//                mDemoSlider.addSlider(textSliderView);
//                if(this.tours.get(position).getAttractions().size() == 1)
//                    Helper.blockSlide(mDemoSlider);
//            }
//        }else{
//            DefaultSliderView textSliderView = new DefaultSliderView(getContext());
//            textSliderView.image(R.drawable.no_photo);
//            mDemoSlider.addSlider(textSliderView);
//            Helper.blockSlide(mDemoSlider);
//        }


        imagesList = (LinearLayout) item.findViewById(R.id.list_images);
        if(imagesList.getChildCount() == 0){
            adapterImages = new GuideTourImageAdapter(getContext(), this.tours.get(position).getAttractions());
            for (int i = 0; i < adapterImages.getCount(); i++) {
                view = adapterImages.getView(i, null, imagesList);
                imagesList.addView(view);
            }
        }

        final ExpandableLayout mExpandableLayout = (ExpandableLayout) item.findViewById(R.id.expandableLayout);
        ElementViewUtils.setText(mExpandableLayout.getHeaderRelativeLayout(), R.id.listTitle, tours.get(position).getName());
        final ImageView rightIcon = (ImageView) mExpandableLayout.getHeaderRelativeLayout().findViewById(R.id.side_arrow);
        rightIcon.setImageResource(R.drawable.ic_expand_more_white_24dp);
        mExpandableLayout.getHeaderRelativeLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mExpandableLayout.isOpened()) {
                    mExpandableLayout.hide();
                    rightIcon.setImageResource(R.drawable.ic_expand_more_white_24dp);
                } else {
                    mExpandableLayout.show();
                    rightIcon.setImageResource(R.drawable.ic_expand_less_white_24dp);
                }

            }
        });


        listPlaces = (LinearLayout) mExpandableLayout.getContentRelativeLayout().findViewById(R.id.guide_tours_list);

        if(listPlaces.getChildCount() == 0) {
            adapter = new GuideTourAdapter(getContext(), this.tours.get(position).getAttractions());
            for (int i = 0; i < adapter.getCount(); i++) {
                view = adapter.getView(i, null, listPlaces);
                listPlaces.addView(view);
            }
        }


//        // Instancia del ListView.
//        ListView guideTourList = (ListView) mExpandableLayout.getContentRelativeLayout().findViewById(R.id.guide_tours_list);
//
//        // Inicializar el adaptador con la fuente de datos.
//        GuideTourAdapter guideToursAdapter = new GuideTourAdapter(getContext(), this.tours.get(position).getAttractions());
//
//        //Relacionando la lista con el adaptador
//        guideTourList.setAdapter(guideToursAdapter);
//        ElementViewUtils.setText(item, R.id.name, tours.get(position).getName());
        ElementViewUtils.setText(item, R.id.title, tours.get(position).getName());

        scroll = (LinearLayout) item.findViewById(R.id.list_images);
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TourActivity.class);
                intent.putExtra("Tour", tours.get(position));
                getContext().startActivity(intent);
            }
        });

        location = (ImageView) item.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
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
