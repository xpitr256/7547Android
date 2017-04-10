package com.example.tallerdyp2.client;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Geocoder;
import android.location.Location;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Proxys.Proxy;
import com.example.tallerdyp2.client.Proxys.ProxyNoLocation;
import com.example.tallerdyp2.client.Proxys.ProxyNormal;
import com.example.tallerdyp2.client.navigationDrawer.DrawerAction;
import com.example.tallerdyp2.client.navigationDrawer.DrawerTransactionManager;
import com.example.tallerdyp2.client.navigationDrawer.Transactional;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.Mocker;
import com.example.tallerdyp2.client.utils.Parser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CityActivity extends AppCompatActivity implements Callable, Transactional, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private GoogleApiClient googleApiClient;
    private List<City> cities;
    private String cityName;
    public City city;
    public Location myLocation;
    private Proxy proxyLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView nvDrawer;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.nav_menu);

        initNavigationDrawer();

        //hide views
        findViewById(R.id.slider).setVisibility(View.GONE);
        findViewById(R.id.header_city).setVisibility(View.GONE);
        findViewById(R.id.header_welcome).setVisibility(View.GONE);
        findViewById(R.id.description_city).setVisibility(View.GONE);
        findViewById(R.id.attractions_list).setVisibility(View.GONE);

        this.cities = new ArrayList<>();
        this.proxyLocation = new ProxyNoLocation();

        //OBTAIN CITY SELECTED, IF COME FROM LIST CITIES
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) cityName = getIntent().getExtras().getString("cityName");


        //POP UP ALLOW LOCATION FOR THE APP
        if(!Helper.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Helper.requestPermission(this,Constants.PERMISSION_LOCATION);
        }else{
            //POP UP ALLOW LOCATION SERVICE
            this.useLocationService();
        }

    }

    protected void initNavigationDrawer(){

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

//        View header = nvDrawer.inflateHeaderView(R.layout.drawer_header);
//        TextView tvHeader = (TextView) header.findViewById(R.id.header_title);
//        tvHeader.setText(SharedPreferencesUtils.getNombreUsuario());
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

//        if(action.allowsTitle) {
//            tvSubtitle.setText(menuItem.getTitle());
//        }

        mDrawer.closeDrawers();
    }

    private String getCityFromLocation(){

        Geocoder geocoder = new Geocoder(CityActivity.this, Locale.getDefault());
        try {
            if(myLocation != null){
                return geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1).get(0).getLocality();
            }
        } catch (IOException e) {
        }


        return null;
    }

    public boolean outsideMyCityLocation(){
        return !this.cityName.equals(this.getCityFromLocation());
    }

    public boolean citySelected(){
        return cityName != null;
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
                this.updateViewCity();
            }
        }
    }

    private void updateViewCity() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ElementViewUtils.setText(findViewById(R.id.header_city),R.id.header_city,city.getName());
//        ElementViewUtils.setImageFromURL(findViewById(R.id.image_view),R.id.image_view,city.getImageURL(),getApplicationContext());
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.removeAllSliders();
        if(!city.getImagesURL().isEmpty()) {
            for (String url : city.getImagesURL()) {
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                textSliderView.image(url);
                mDemoSlider.addSlider(textSliderView);
            }
        }else{
            DefaultSliderView textSliderView = new DefaultSliderView(this);
            textSliderView.image(R.drawable.no_photo);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        ElementViewUtils.setText(findViewById(R.id.description_city),R.id.description_city,city.getDescription());

        this.updateViewAttractions();

        findViewById(R.id.slider).setVisibility(View.VISIBLE);
        findViewById(R.id.header_city).setVisibility(View.VISIBLE);
        findViewById(R.id.header_welcome).setVisibility(View.VISIBLE);
        findViewById(R.id.description_city).setVisibility(View.VISIBLE);


    }

    private void updateViewAttractions(){
        LinearLayout list = (LinearLayout) findViewById(R.id.attractions_list);
        list.removeAllViews();
        if(!city.getAttractions().isEmpty()){
            Helper.updateAtractionsDistanceFromMyLocation(city.getAttractions(), this.proxyLocation.getLatitude(this), this.proxyLocation.getLongitude(this));
            Helper.sortAttractions(city.getAttractions());
            AttractionsAdapter adapter = new AttractionsAdapter(this, city.getAttractions());
            for (int i = 0; i < adapter.getCount(); i++) {
                View view = adapter.getView(i, null, list);
                list.addView(view);
            }
            findViewById(R.id.attractions_list).setVisibility(View.VISIBLE);
        }else
            findViewById(R.id.no_attractions).setVisibility(View.VISIBLE);
    }

    private void useLocationService() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        CityActivity.this.proxyLocation = new ProxyNormal();
                        CityActivity.this.proxyLocation.execute(CityActivity.this);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(CityActivity.this, Constants.LOCATION_SERVICE);
                        } catch (IntentSender.SendIntentException e) {
                            CityActivity.this.showError(getResources().getString(R.string.location_denied));
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        CityActivity.this.showError(getResources().getString(R.string.location_denied));
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onConnected(Bundle arg0) {
        if (Helper.checkSelfPermission(CityActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)) {

            fusedLocationProviderApi.requestLocationUpdates(googleApiClient,  locationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Location locAux = myLocation;
        myLocation = location;

        if(hasToRefreshView(locAux, location)){
            if(this.city != null){
                this.updateViewAttractions();
            }
        }
    }

    private boolean hasToRefreshView(Location locAux, Location location) {
        if(locAux == null) return true;
        if(Helper.distance(locAux.getLatitude(), location.getLatitude(), locAux.getLongitude(), location.getLongitude(), 0.0, 0.0) > Constants.MIN_DIST_REFRESH_LOC)
            return true;

        return false;
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void getCitiesInfo() {
        AttractionGOApplication.getVolleyRequestService().getCities(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_attraction, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.cities:
////                Intent intent = new Intent(CityActivity.this, CitiesActivity.class);
////                startActivity(intent);
//                AttractionGOApplication.getFacebookService().logOut();
//                break;
//        }
//        return true;
//    }

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
        findViewById(R.id.image_error).setVisibility(View.VISIBLE);
        ElementViewUtils.setText(findViewById(R.id.textErrorRequest), R.id.textErrorRequest, error);
        findViewById(R.id.textErrorRequest).setVisibility(View.VISIBLE);
    }

    private void hideInformation() {
        //hide views
        findViewById(R.id.slider).setVisibility(View.GONE);
        findViewById(R.id.header_city).setVisibility(View.GONE);
        findViewById(R.id.header_welcome).setVisibility(View.GONE);
        findViewById(R.id.description_city).setVisibility(View.GONE);
        findViewById(R.id.attractions_list).setVisibility(View.GONE);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Constants.LOCATION_SERVICE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                this.proxyLocation = new ProxyNormal();
                this.proxyLocation.execute(this);
                // Do something with the contact here (bigger example below)
            }else{
                this.proxyLocation = new ProxyNoLocation();
                this.proxyLocation.execute(this);
            }
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
                    this.proxyLocation = new ProxyNoLocation();
                    this.proxyLocation.execute(this);
                }
                return;
            }
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        //POP UP ALLOW LOCATION FOR THE APP
        if(!Helper.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Helper.requestPermission(this,Constants.PERMISSION_LOCATION);
        }else{
            //POP UP ALLOW LOCATION SERVICE
            this.useLocationService();
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
                        AttractionGOApplication.getFacebookService().logOut();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }

    @Override
    protected void onStop() {
        if(mDemoSlider != null) mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}