package com.example.tallerdyp2.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.TabsAdapter;
import com.example.tallerdyp2.client.builders.TabFragmentBuilder;
import com.example.tallerdyp2.client.customViews.SlidingTabLayout;
import com.example.tallerdyp2.client.ui.fragments.attraction.DescriptionATFragment;
import com.example.tallerdyp2.client.ui.fragments.attraction.LocationATFragment;
import com.example.tallerdyp2.client.ui.fragments.attraction.PointOfInterestFragment;
import com.example.tallerdyp2.client.ui.fragments.attraction.ReviewFragment;
import com.example.tallerdyp2.client.utils.ElementViewUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sebastian on 23/3/2017.
 */

public class AttractionActivity extends AppCompatActivity{

    private Attraction attraction;
    protected TabsAdapter tabPagerAdapter;
    protected SlidingTabLayout tabs;

    protected List<Integer> tabsDrawablesId;
    protected List<Fragment> fragments;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabsDrawablesId = new ArrayList<>();
        fragments = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_attraction);
        attraction = (Attraction) getIntent().getSerializableExtra("Attraction");

        getSupportActionBar().setTitle(attraction.getName());

        setViewPager();
    }

    private void setViewPager() {

        setTabsAndFragments();

        tabPagerAdapter =  new TabsAdapter(getSupportFragmentManager(), fragments, tabsDrawablesId);

        viewPager = (ViewPager) findViewById(R.id.view_pager_attraction);
        viewPager.setAdapter(tabPagerAdapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs_attraction);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.grey);
            }
        });
        tabs.setViewPager(viewPager);
    }

    private void setTabsAndFragments() {

        tabsDrawablesId.clear();
        fragments.clear();

        Map<Integer, TabFragmentBuilder> tabsFragments = initTabFragments();

        for(int tab: tabsFragments.keySet()){
            tabsDrawablesId.add(tab);
            fragments.add(tabsFragments.get(tab).buildFragment());
        }
    }

    private Map<Integer, TabFragmentBuilder> initTabFragments(){
        return new LinkedHashMap<Integer, TabFragmentBuilder>(){{
            put(R.drawable.ic_home, new TabFragmentBuilder<>(new DescriptionATFragment(),
                    getString(R.string.description_at), attraction));
            put(R.drawable.ic_place, new TabFragmentBuilder<>(new LocationATFragment(),
                    getString(R.string.location_at), attraction));
            put(R.drawable.ic_comment, new TabFragmentBuilder<>(new ReviewFragment(),
                    getString(R.string.review_at), attraction));
            put(R.drawable.ic_star, new TabFragmentBuilder<>(new PointOfInterestFragment(),
                    getString(R.string.poi_at), attraction));
        }};
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
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onNewIntent (Intent intent){
        finish();
        startActivity(intent);
    }

}
