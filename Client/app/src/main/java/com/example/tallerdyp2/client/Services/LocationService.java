package com.example.tallerdyp2.client.Services;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Proxys.ProxyNormal;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.activities.CityActivity;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.LocationCallable;
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

import org.w3c.dom.Attr;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by Sebastian on 14/4/2017.
 */

public class LocationService  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{

    private LocationRequest locationRequest;
    private FusedLocationProviderApi fusedLocationProviderApi;
    private GoogleApiClient googleApiClient;
    private LocationCallable myCall;
    private Location myLocation;

    public void activeLocation(LocationCallable call){
        this.myCall = call;
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApiClient = new GoogleApiClient.Builder(AttractionGOApplication.getAppContext())
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
                        myCall.onLocationSuccess();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        myCall.onResolutionRequired(status);
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        myCall.onLocationFail();
                        break;
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle arg0) {
        if (Helper.checkSelfPermission(AttractionGOApplication.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            fusedLocationProviderApi.requestLocationUpdates(googleApiClient,  locationRequest, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Location locAux = myLocation;
        myLocation = location;

        if(hasToRefreshView(locAux, location)){
            myCall.onLocationChange();
        }
    }

    public Location getLocation(){
        return this.myLocation;
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

    public String getCityFromLocation() {
        Geocoder geocoder = new Geocoder(AttractionGOApplication.getAppContext(), Locale.getDefault());
        try {
            if(myLocation != null){
                return geocoder.getFromLocation(myLocation.getLatitude(), myLocation.getLongitude(), 1).get(0).getLocality();
            }
        } catch (IOException e) {
        }
        return null;
    }
}
