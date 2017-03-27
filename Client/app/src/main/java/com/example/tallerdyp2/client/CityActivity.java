package com.example.tallerdyp2.client;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Proxys.Proxy;
import com.example.tallerdyp2.client.Proxys.ProxyNoLocation;
import com.example.tallerdyp2.client.Proxys.ProxyNormal;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.Parser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
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

public class CityActivity extends AppCompatActivity implements Callable,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    private List<City> cities;
    private String cityName;
    public City city;
    public Location myLocation;
    private Proxy proxyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        //hide views
        findViewById(R.id.image_view).setVisibility(View.GONE);
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

    public boolean outsideMyCityLocation(){
        Geocoder geocoder = new Geocoder(CityActivity.this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
            return !addresses.get(0).getLocality().equals(this.cityName);

        } catch (IOException e) {
        }
        return true;
    }

    public boolean citySelected(){
        return cityName != null;
    }

    public void getMyCityLocation(){
        if (Helper.checkSelfPermission(CityActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)) {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            Geocoder geocoder = new Geocoder(CityActivity.this, Locale.getDefault());
            List<Address> addresses;
            try {
                if(this.cityName == null){
                    addresses = geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1);
                    this.cityName = addresses.get(0).getLocality();
                }
                this.getMyCity();
            } catch (IOException e) {
                this.showError(getResources().getString(R.string.error_request));
            }

        }else{
            this.showError(getResources().getString(R.string.location_denied));
        }
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
        ElementViewUtils.setImage(findViewById(R.id.image_view),R.id.image_view,city.getImageURL(),getApplicationContext());
        ElementViewUtils.setText(findViewById(R.id.description_city),R.id.description_city,city.getDescription());
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

        findViewById(R.id.image_view).setVisibility(View.VISIBLE);
        findViewById(R.id.header_city).setVisibility(View.VISIBLE);
        findViewById(R.id.header_welcome).setVisibility(View.VISIBLE);
        findViewById(R.id.description_city).setVisibility(View.VISIBLE);


    }

    private void useLocationService() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(CityActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
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

    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void getCitiesInfo() {
        AttractionGOApplication.getVolleyRequestService().getCities(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cities:
                Intent intent = new Intent(CityActivity.this, CitiesActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void execute(JSONArray response) {
        cities = Parser.parseCities(response);
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
        findViewById(R.id.image_view).setVisibility(View.GONE);
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
}