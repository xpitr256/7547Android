package com.example.tallerdyp2.client;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.ElementViewUtils;
import com.example.tallerdyp2.client.utils.Helper;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InitialActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    private List<City> cities;
    private String citySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        //hide views
        findViewById(R.id.image_view).setVisibility(View.GONE);
        findViewById(R.id.header_city).setVisibility(View.GONE);
        findViewById(R.id.description_city).setVisibility(View.GONE);

        this.cities = new ArrayList<>();

        //OBTAIN CITY SELECTED, IF COME FROM LIST CITIES
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) citySelected = getIntent().getExtras().getString("city");

        //POP UP ALLOW LOCATION FOR THE APP
        Helper.requestPermission(this,Constants.PERMISSION_LOCATION);

        //POP UP ALLOW LOCATION SERVICE
        this.useLocationService();

    }

    private void getMyCityLocation(){
        if (Helper.checkSelfPermission(InitialActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            Geocoder geocoder = new Geocoder(InitialActivity.this, Locale.getDefault());
            List<Address> addresses;
            try {
                if(citySelected == null){
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    this.getMyCity(addresses.get(0).getLocality());
                }else this.getMyCity(citySelected);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void getMyCity(String cityName) {
        for(City city : cities){
            if(city.getName().equals(cityName)){
                this.updateViewCity(city);
            }
        }
    }

    private void updateViewCity(City city) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        ElementViewUtils.setText(findViewById(R.id.header_city),R.id.header_city,city.getName());
        ElementViewUtils.setImage(findViewById(R.id.image_view),R.id.image_view,city.getImageURL(),getApplicationContext());
        ElementViewUtils.setText(findViewById(R.id.description_city),R.id.description_city,city.getDescription());

        findViewById(R.id.image_view).setVisibility(View.VISIBLE);
        findViewById(R.id.header_city).setVisibility(View.VISIBLE);
        findViewById(R.id.description_city).setVisibility(View.VISIBLE);

    }

    private void useLocationService() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(InitialActivity.this)
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
                            InitialActivity.this.getCitiesInfo();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        InitialActivity.this, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
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
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.http_ip) + "/city";
        final Context context = getApplicationContext();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Inicializar el adaptador con la fuente de datos.
                cities = procesarResponse(response);
                getMyCityLocation();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error de conexion", Toast.LENGTH_LONG).show();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    public List<City> procesarResponse(JSONArray response) {
        List<City> cities = new ArrayList<>();
        JSONObject city;
        for(int i  = 0; i < response.length(); i++) {
            try {
                city = response.getJSONObject(i);
                cities.add(new City(city.getString("_id"),
                        city.getString("name"),
                        city.getString("description"),
                        city.getString("imageURL"),
                        city.getJSONObject("location").getDouble("lat"),
                        city.getJSONObject("location").getDouble("lng")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return cities;
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
                Intent intent = new Intent(InitialActivity.this, CityActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}