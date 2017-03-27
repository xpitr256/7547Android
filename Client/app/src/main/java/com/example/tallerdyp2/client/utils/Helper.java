package com.example.tallerdyp2.client.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.tallerdyp2.client.CityActivity;
import com.example.tallerdyp2.client.Entities.Attraction;

import java.text.Normalizer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sebastian on 18/3/2017.
 */

public class Helper {

    public static void requestPermission(Activity activity, int typePermission) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, typePermission);
        }
    }

    public static boolean checkSelfPermission(Activity activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static void updateAtractionsDistanceFromMyLocation(List<Attraction> attractions, double lat, double lng) {
        for(Attraction attraction: attractions){
            attraction.updateDistance(lat,lng);
        }
    }

    public static void sortAttractions(List<Attraction> attractions){
        Collections.sort(attractions, new Comparator<Attraction>() {
            @Override
            public int compare(Attraction a1, Attraction a2) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return a1.getDistance() > a2.getDistance() ? 1 : (a1.getDistance() < a2.getDistance() ) ? -1 : 0;
            }
        });
    }

    public static String formatDistance(double distance) {
        if(distance >= 1000){
            distance /= 1000;
            if(distance > 10000)
                return "+9999km";
            return Integer.toString((int) distance)+" kms";
        }else return Integer.toString((int) distance)+" mts";
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

}
