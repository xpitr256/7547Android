package com.example.tallerdyp2.client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.TabsAdapter;
import com.example.tallerdyp2.client.builders.TabFragmentBuilder;
import com.example.tallerdyp2.client.customViews.SlidingTabLayout;
import com.example.tallerdyp2.client.ui.fragments.attraction.DescriptionFragment;
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

    protected List<CharSequence> tabsTitles;
    protected List<Fragment> fragments;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabsTitles = new ArrayList<>();
        fragments = new ArrayList<>();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        setContentView(R.layout.activity_attraction);
        attraction = (Attraction) getIntent().getSerializableExtra("Attraction");

        ElementViewUtils.setText(findViewById(R.id.header_attraction),R.id.header_attraction,attraction.getName());

        setViewPager();
    }

    private void setViewPager() {

        setTabsAndFragments();

        tabPagerAdapter =  new TabsAdapter(getSupportFragmentManager(), fragments, tabsTitles);

        viewPager = (ViewPager) findViewById(R.id.view_pager_attraction);
        viewPager.setAdapter(tabPagerAdapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs_attraction);
        tabs.setDistributeEvenly(false);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.grey);
            }
        });
        tabs.setViewPager(viewPager);
    }

    private void setTabsAndFragments() {

        tabsTitles.clear();
        fragments.clear();

        Map<String, TabFragmentBuilder> tabsFragments = initTabFragments();

        for(String tab: tabsFragments.keySet()){
            tabsTitles.add(tab);
            fragments.add(tabsFragments.get(tab).buildFragment());
        }
    }

    private Map<String, TabFragmentBuilder> initTabFragments(){
        return new LinkedHashMap<String, TabFragmentBuilder>(){{
            put(getString(R.string.description_at), new TabFragmentBuilder<>(new DescriptionFragment(),
                    getString(R.string.description_at), attraction));
            put(getString(R.string.review_at), new TabFragmentBuilder<>(new ReviewFragment(),
                    getString(R.string.review_at), attraction));
        }};
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.review:
                Intent intent = new Intent(this, MyReviewActivity.class);
                intent.putExtra("Attraction", attraction);
                startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_attraction, menu);
        return true;
    }

}
