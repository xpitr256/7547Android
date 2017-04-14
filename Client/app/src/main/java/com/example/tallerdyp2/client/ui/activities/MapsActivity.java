package com.example.tallerdyp2.client.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.LocationCallable;
import com.example.tallerdyp2.client.utils.PicassoMarker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class MapsActivity extends AppCompatActivity implements LocationCallable, OnMapReadyCallback{

    private GoogleMap mMap;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        city = (City) getIntent().getSerializableExtra("City");

        //POP UP ALLOW LOCATION FOR THE APP
        if(!Helper.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Helper.requestPermission(this, Constants.PERMISSION_LOCATION);
        }else{
            //POP UP ALLOW LOCATION SERVICE
            this.useLocationService();
        }

    }

    private void initMap(){
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(Helper.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
            mMap = googleMap;
            mMap.setMyLocationEnabled(true);

            LatLng currentLoc = new LatLng(AttractionGOApplication.getLocationService().getLocation().getLatitude(), AttractionGOApplication.getLocationService().getLocation().getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLoc).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));

            Toast.makeText(this, AttractionGOApplication.getLocationService().getLocation().getLatitude() + " WORKS " + AttractionGOApplication.getLocationService().getLocation().getLongitude() + "", Toast.LENGTH_LONG).show();

            Marker marker;
            for(final Attraction attraction : city.getAttractions()){
                currentLoc = new LatLng(attraction.getLatitude(), attraction.getLongitude());
                marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title(attraction.getName()));
                marker.setTag(attraction.getId());
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String id = (String)(marker.getTag());
                        for(Attraction attraction : city.getAttractions()){
                            if(attraction.getId().equals(id)){
                                Intent intent = new Intent(AttractionGOApplication.getAppContext(), AttractionActivity.class);
                                intent.putExtra("Attraction", attraction);
                                AttractionGOApplication.getAppContext().startActivity(intent);
                            }
                        }
                        return true;
                    }
                });
                PicassoMarker pmarker = new PicassoMarker(marker);
                Picasso.with(MapsActivity.this).load(attraction.getImagesURL().get(0)).resize(200,200).into(pmarker);
            }
        }
    }

//    Marker marker =  map.addMarker(new MarkerOptions()
//            .position(new LatLng(latitude, longitude)));
//marker.setTag(position);
//    getTag() on setOnMarkerClickListener listener
//
//map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//        @Override
//        public boolean onMarkerClick(Marker marker) {
//            int position = (int)(marker.getTag());
//            //Using position get Value from arraylist
//            return false;
//        }
//    });

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(Helper.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                        mMap.setMyLocationEnabled(true);
                        this.useLocationService();
                    }
                }else{
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Constants.LOCATION_SERVICE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                this.useLocationService();
            }else{
                this.finish();
            }
        }
    }

    private void useLocationService() {
        AttractionGOApplication.getLocationService().activeLocation(this);
    }

    @Override
    public void onLocationSuccess() {
        this.initMap();
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
            this.finish();
        }
    }

    @Override
    public void onLocationChange() {
        Toast.makeText(this, AttractionGOApplication.getLocationService().getLocation().getLatitude() + " WORKS " + AttractionGOApplication.getLocationService().getLocation().getLongitude() + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationFail() {
        // Location settings are not satisfied. However, we have no way to fix the
        // settings so we won't show the dialog.
        this.finish();
    }
}
