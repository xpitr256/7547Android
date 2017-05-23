package com.example.tallerdyp2.client.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Proxys.ProxyNormal;
import com.example.tallerdyp2.client.builders.TabFragmentBuilder;
import com.example.tallerdyp2.client.customViews.SlidingTabLayout;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Proxys.Proxy;
import com.example.tallerdyp2.client.Proxys.ProxyNoLocation;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.navigationDrawer.DrawerAction;
import com.example.tallerdyp2.client.navigationDrawer.DrawerTransactionManager;
import com.example.tallerdyp2.client.navigationDrawer.Transactional;
import com.example.tallerdyp2.client.ui.adapters.TabsAdapter;
import com.example.tallerdyp2.client.ui.fragments.city.DescriptionCIFragment;
import com.example.tallerdyp2.client.ui.fragments.city.TourCIFragment;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.LocationCallable;
import com.example.tallerdyp2.client.utils.Parser;
import com.example.tallerdyp2.client.utils.SharedPreferencesUtils;
import com.google.android.gms.common.api.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CityActivity extends AppCompatActivity implements Callable, Transactional, LocationCallable{

    private List<City> cities;
    private String cityName;
    public City city;
    private Proxy proxyLocation;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nvDrawer;
    private DescriptionCIFragment descriptionFragment;

    protected TabsAdapter tabPagerAdapter;
    protected SlidingTabLayout tabs;

    protected List<Integer> tabsDrawablesId;
    protected List<Fragment> fragments;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AttractionGOApplication.getLanguageService().setLanguage(SharedPreferencesUtils.getLanguage());

        setContentView(R.layout.activity_city);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.nav_menu);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        initNavigationDrawer();

        this.cities = new ArrayList<>();
        this.proxyLocation = new ProxyNoLocation();

        //OBTAIN CITY SELECTED, IF COME FROM LIST CITIES
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) cityName = getIntent().getExtras().getString("cityName");

        String language = SharedPreferencesUtils.getLanguage();

        //TABS
        tabsDrawablesId = new ArrayList<>();
        fragments = new ArrayList<>();

        descriptionFragment = new DescriptionCIFragment();

        //POP UP ALLOW LOCATION FOR THE APP
        if(!Helper.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Helper.requestPermission(this,Constants.PERMISSION_LOCATION);
        }else{
            //POP UP ALLOW LOCATION SERVICE
            this.useLocationService();
        }

    }


    private void setViewPager() {

        getSupportActionBar().setTitle(cityName);
        this.descriptionFragment.setCitySelected(cityName);

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
            put(R.drawable.ic_home, new TabFragmentBuilder<>(descriptionFragment,
                    getString(R.string.description_at), city));
            put(R.drawable.ic_directions, new TabFragmentBuilder<>(new TourCIFragment(),
                    getString(R.string.tour_ci), city));
        }};
    }

    protected void initNavigationDrawer(){
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.left_drawer);
        setupDrawerContent(nvDrawer);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawer,         /* DrawerLayout object */
                R.drawable.nav_menu,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
//                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawer.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectItem(menuItem);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(!mDrawer.isDrawerOpen(GravityCompat.START)){
                    mDrawer.openDrawer(Gravity.START);  // OPEN DRAWER
                }else{
                    mDrawer.closeDrawer(Gravity.LEFT);  // CLOSE DRAWER
                }
                return true;
//            case R.id.map:
//                Intent intent = new Intent(this, TourActivity.class);
//                intent.putExtra("City", city);
//                startActivity(intent);
//                break;

        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(MenuItem menuItem) {

        DrawerAction action = DrawerAction.findByCode(menuItem.getItemId());
        DrawerTransactionManager.getInstance().startTransaction(action, this);

        menuItem.setChecked(true);

        mDrawer.closeDrawers();
    }



    public boolean citySelected(){
        return cityName != null;
    }

    private String getCityFromLocation(){
        return AttractionGOApplication.getLocationService().getCityFromLocation();
    }

    public void getMyCityLocation(){
        if(this.cityName == null){
            String city = this.getCityFromLocation();
            if(city != null){
                this.cityName = city;
            }else{
                this.cityName = this.cities.get(0).getName();
            }
        }
        this.getMyCity();
    }

    public void getMyCity() {
        for(City city : cities){
            if(city.getName().equals(this.cityName)){
                this.city = city;
                this.showContent();
                this.setViewPager();
            }
        }
    }

    private void showContent() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.content).setVisibility(View.VISIBLE);
    }

    private void useLocationService() {
        AttractionGOApplication.getLocationService().activeLocation(this);
    }

    @Override
    public void onBackPressed() {}

    public void getCitiesInfo() {
        AttractionGOApplication.getVolleyRequestService().getCities(this);
    }

    @Override
    public void execute(JSONArray response) {
        cities = Parser.parseCities(response);
//        cities = Mocker.parseCities(response);
        this.proxyLocation.getCity(this);
    }

    @Override
    public void execute(JSONObject response) {

    }

    @Override
    public void error(VolleyError error) {
        this.showError(getResources().getString(R.string.error_request));
    }

    private void showError(String error) {
        this.hideInformation();
        findViewById(R.id.error).setVisibility(View.VISIBLE);
        ElementViewUtils.setText(findViewById(R.id.textErrorRequest), R.id.textErrorRequest, error);
    }

    private void hideInformation() {
        //hide views
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        findViewById(R.id.content).setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Constants.LOCATION_SERVICE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                this.descriptionFragment.setProxyLocation();
                this.proxyLocation = new ProxyNormal();

                // Do something with the contact here (bigger example below)
            }else{
                this.descriptionFragment.setProxyNoLocation();
                this.proxyLocation = new ProxyNoLocation();
            }
            this.proxyLocation.execute(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.useLocationService();
                }else {
                    this.descriptionFragment.setProxyNoLocation();
                    this.proxyLocation = new ProxyNoLocation();
                    this.proxyLocation.execute(this);
                }
                return;
            }
        }
    }

    @Override
    public void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        this.startActivity(intent);
    }

    @Override
    public void closeSession() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.msg_close_session)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        AttractionGOApplication.getSplexService().logOut();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    @Override
    public void changeLanguage() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.choose_language))
                .setSingleChoiceItems(AttractionGOApplication.getLanguageService().getIdiomsName(), AttractionGOApplication.getLanguageService().getIdiomSelected(), new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        AttractionGOApplication.getLanguageService().setLanguage(AttractionGOApplication.getLanguageService().getIdiomCode(item));
                        dialog.dismiss();
                        restart();
                    }
                }).show();
    }

    private void restart(){
        Intent intent = getIntent();
        intent.putExtra("cityName", this.cityName);
        finish();
        startActivity(intent);
    }

    @Override
    public void onLocationSuccess() {
        this.descriptionFragment.setProxyLocation();
        this.proxyLocation = new ProxyNormal();
        this.proxyLocation.execute(this);
    }

    @Override
    public void onResolutionRequired(Status status) {
        // Location settings are not satisfied. But could be fixed by showing the user
        // a dialog.

        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().

            status.startResolutionForResult(this, Constants.LOCATION_SERVICE);
        } catch (IntentSender.SendIntentException e) {
           this.showError(getResources().getString(R.string.location_denied));
        }
    }

    @Override
    public void onLocationChange() {
        if(this.city != null){
            this.descriptionFragment.updateViewAttractions();
        }
    }

    @Override
    public void onLocationFail() {
        // Location settings are not satisfied. However, we have no way to fix the
        // settings so we won't show the dialog.
        this.showError(getResources().getString(R.string.location_denied));
    }

}