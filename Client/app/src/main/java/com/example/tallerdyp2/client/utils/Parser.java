package com.example.tallerdyp2.client.utils;

import android.icu.text.SimpleDateFormat;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;
import com.example.tallerdyp2.client.Entities.PointOfInterest;
import com.example.tallerdyp2.client.Entities.Review;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sebastian on 24/3/2017.
 */

public class Parser {
    public static List<City> parseCities(JSONArray response) {
        List<City> cities = new ArrayList<>();
        List<Attraction> attractions;
        JSONArray attractionsJSON;
        JSONObject city;
        try {
            for(int i  = 0; i < response.length(); i++) {

                city = response.getJSONObject(i);
                attractions = new ArrayList<>();
                attractionsJSON = city.getJSONArray("attractions");
                for(int j = 0; j < attractionsJSON.length(); j++){
                    attractions.add(Parser.parseAttraction(attractionsJSON.getJSONObject(j)));
                }
                cities.add(new City(city.getString("_id"),
                        city.getString("name"),
                        city.getString("description"),
                        Parser.parseImagesURL(city.getJSONArray("imagesURL")),
                        city.getJSONObject("location").getDouble("lat"),
                        city.getJSONObject("location").getDouble("lng"), attractions));

            }
        } catch (JSONException e) {
            return cities;
        }
        return cities;
    }

    private static List<PointOfInterest> parsePOIs(JSONArray poisJson) throws JSONException {
        List<PointOfInterest> pois = new ArrayList<>();
        for(int i = 0; i < poisJson.length(); i++){
            pois.add(new PointOfInterest(
                    poisJson.getJSONObject(i).getString("_id"),
                    poisJson.getJSONObject(i).getString("name"),
                    poisJson.getJSONObject(i).getString("description"),
                    Parser.parseImagesURL(poisJson.getJSONObject(i).getJSONArray("imagesURL")),
                    poisJson.getJSONObject(i).getString("audioURL")
            ));
        }
        return pois;
    }

    public static List<String> parseImagesURL(JSONArray imagesURL) throws JSONException {
        List<String> urls = new ArrayList<>();
        for(int i = 0; i < imagesURL.length(); i++){
            urls.add(imagesURL.getString(i));
        }
        return urls;
    }

    public static List<Review> parseReviews(JSONArray reviewsJson) throws JSONException {
        List<Review> reviews = new ArrayList<>();
        for(int i = 0; i < reviewsJson.length(); i++){
            reviews.add(new Review(
                    reviewsJson.getJSONObject(i).getString("userName"),
                    reviewsJson.getJSONObject(i).getString("userId"),
                    reviewsJson.getJSONObject(i).getString("userAvatarUrl"),
                    reviewsJson.getJSONObject(i).getString("comments"),
                    reviewsJson.getJSONObject(i).getDouble("rating"),
                    Helper.getDate(reviewsJson.getJSONObject(i).getString("date")),
                    reviewsJson.getJSONObject(i).getBoolean("approved")
            ));
        }
        return reviews;
    }

    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public static List<List<HashMap<String,String>>> parsePath(JSONObject jObject){

        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++){
                        String polyline = "";
                        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = Parser.decodePoly(polyline);

                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }

        return routes;
    }
    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private static List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public static Attraction parseAttraction(JSONObject attractionJson) throws JSONException {
        return new Attraction(
                attractionJson.getString("_id"),
                attractionJson.getString("name"),
                attractionJson.getString("description"),
                attractionJson.getDouble("rating"),
                Parser.parseImagesURL(attractionJson.getJSONArray("imagesURL")),
                attractionJson.getString("audioURL"),
                attractionJson.getJSONObject("location").getDouble("lat"),
                attractionJson.getJSONObject("location").getDouble("lng"),
                "FAMILY",
                "00:00",
                "00:00",
//                                    attractionJson.getString("type"),
//                                    attractionJson.getString("openTime"),
//                                    attractionJson.getString("closeTime"),
                attractionJson.getInt("price"),
                Parser.parseReviews(attractionJson.getJSONArray("reviews")),
//                                    Parser.parsePOIs(attractionJson.getJSONArray("pois"))
                Mocker.parsePOIs()
        );

    }
}
