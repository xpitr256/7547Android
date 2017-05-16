package com.example.tallerdyp2.client.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.tallerdyp2.client.AttractionGOApplication;
import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.Tour;
import com.example.tallerdyp2.client.R;
import com.example.tallerdyp2.client.ui.adapters.MarkerPopupAdapter;
import com.example.tallerdyp2.client.utils.Callable;
import com.example.tallerdyp2.client.utils.Constants;
import com.example.tallerdyp2.client.utils.Helper;
import com.example.tallerdyp2.client.utils.LocationCallable;
import com.example.tallerdyp2.client.utils.Parser;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TourActivity extends AppCompatActivity implements LocationCallable, Callable, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;
    private Tour tour;
    private Marker markerSelected;
    private ArrayList<LatLng> points;
    private Map<Integer, Attraction> markersAttractionMap = new HashMap<Integer, Attraction>();
    private Map<Integer, Marker> markers = new HashMap<Integer, Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        points = new ArrayList<>();
        tour = (Tour) getIntent().getSerializableExtra("Tour");

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
        mMap = googleMap;
        if(Helper.checkSelfPermission(TourActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mMap.setMyLocationEnabled(true);
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor( "#CC0000FF" ) );
        options.width( 5 );
        options.visible( true );

        //Set my custom info window MarkerPopupAdapter
        GoogleMap.InfoWindowAdapter customInfoWindow = new MarkerPopupAdapter(getLayoutInflater(), markersAttractionMap, markerSelected);
        mMap.setInfoWindowAdapter(customInfoWindow);

        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);

        LatLng currentLoc;
        Marker marker;
        int count = 0;
        for(final Attraction attraction : tour.getAttractions()){
            currentLoc = new LatLng(attraction.getLatitude(), attraction.getLongitude());

            points.add(currentLoc);

            Bitmap bitmap = Helper.GetBitmapMarker(getBaseContext(), R.drawable.marker, Integer.toString(count+1));

            marker = mMap.addMarker(new MarkerOptions().position(currentLoc).title(attraction.getName()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)).snippet(attraction.getImagesURL().get(0)));

            marker.setTag(count);

            options.add(currentLoc);
            markersAttractionMap.put(count,attraction);
            markers.put(count,marker);

            ++count;
        }

        mMap.addPolyline( options );
        this.focusPosition(tour.getAttractions().get(0).getLatitude(),tour.getAttractions().get(0).getLongitude());

        this.markerSelected = this.markers.get(0);

        final FloatingActionButton previous = (FloatingActionButton) findViewById(R.id.fab_previous);
        final FloatingActionButton next = (FloatingActionButton) findViewById(R.id.fab_next);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = (int)markerSelected.getTag();
                if(selected > 0){
                    next.setVisibility(View.VISIBLE);
                    --selected;
                    markerSelected = markers.get(selected);
                    focusPosition(markersAttractionMap.get(selected).getLatitude(),markersAttractionMap.get(selected).getLongitude());
                    if( selected == 0) previous.setVisibility(View.GONE);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = (int)markerSelected.getTag();
                if(selected < tour.getAttractions().size()-1){
                    previous.setVisibility(View.VISIBLE);
                    ++selected;
                    markerSelected = markers.get(selected);
                    focusPosition(markersAttractionMap.get(selected).getLatitude(),markersAttractionMap.get(selected).getLongitude());
                    if( selected == tour.getAttractions().size()-1) next.setVisibility(View.GONE);
                }
            }
        });

        previous.setVisibility(View.GONE);
    }

    private void focusPosition(double lat, double lon){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(lat, lon))
                .zoom(17)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

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
                    this.initMap();
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
                this.initMap();
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
            this.initMap();
        }
    }

    @Override
    public void onLocationChange() {
    }

    @Override
    public void onLocationFail() {
        this.initMap();
    }

    @Override
    public void execute(JSONArray response) {

    }

    @Override
    public void execute(JSONObject response) {
        try {
            Intent intent = new Intent(getApplicationContext(), AttractionActivity.class);
            intent.putExtra("Attraction", Parser.parseAttraction(response));
            finish();
            startActivity(intent);
        } catch (JSONException e) {
            this.error(null);
        }
    }

    @Override
    public void error(VolleyError error) {

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
    public void onInfoWindowClick(Marker marker) {
        AttractionGOApplication.getVolleyRequestService().getAttraction(this, this.markersAttractionMap.get(marker.getTag()).getId());
    }


    //CODIGO QUE PERMITE DIBUJAR EL RECORRIDO POR UNA RUTA VALIDA DE UN PUNTO A OTRO
//    private void getPaths() {
//
//        List<String> paths = new ArrayList<>();
//        for(int i=0; i<points.size()-1; ++i){
//            // Origin of route
//            String str_origin = "origin=" + points.get(i).latitude + "," + points.get(i).longitude;
//
//            // Destination of route
//            String str_dest = "destination=" + points.get(i+1).latitude + "," + points.get(i+1).longitude;
//
//            // Sensor enabled
//            String key = "key=" + getString(R.string.google_maps_key);
//
//            // Building the parameters to the web service
//            String parameters = str_origin + "&" + str_dest + "&" + key;
//
//            // Output format
//            String output = "json";
//
//            // Building the url to the web service
//            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
//
//
//            AttractionGOApplication.getVolleyRequestService().getPath(url,this);
////            paths.add(url);
//        }
//
////        AttractionGOApplication.getVolleyRequestService().getPaths(paths,this);
//    }

    //    @Override
//    public void execute(JSONObject response) {
//        List<List<HashMap<String,String>>> routes = Parser.parsePath(response);
//        ArrayList<LatLng> points = null;
//        PolylineOptions lineOptions = null;
//        MarkerOptions markerOptions = new MarkerOptions();
//
//        // Traversing through all the routes
//        for(int i=0;i<routes.size();i++){
//            points = new ArrayList<LatLng>();
//            lineOptions = new PolylineOptions();
//
//            // Fetching i-th route
//            List<HashMap<String, String>> path = routes.get(i);
//
//            // Fetching all the points in i-th route
//            for(int j=0;j<path.size();j++){
//                HashMap<String,String> point = path.get(j);
//
//                double lat = Double.parseDouble(point.get("lat"));
//                double lng = Double.parseDouble(point.get("lng"));
//                LatLng position = new LatLng(lat, lng);
//
//                points.add(position);
//            }
//
//            // Adding all the points in the route to LineOptions
//            lineOptions.addAll(points);
//            lineOptions.width(2);
//            lineOptions.color(Color.RED);
//        }
//
//        // Drawing polyline in the Google Map for the i-th route
//        mMap.addPolyline(lineOptions);
//    }
}
