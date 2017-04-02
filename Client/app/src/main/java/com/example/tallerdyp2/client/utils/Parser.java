package com.example.tallerdyp2.client.utils;

import com.example.tallerdyp2.client.Entities.Attraction;
import com.example.tallerdyp2.client.Entities.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
                    attractions.add(
                            new Attraction(
                                    attractionsJSON.getJSONObject(j).getString("_id"),
                                    attractionsJSON.getJSONObject(j).getString("name"),
                                    attractionsJSON.getJSONObject(j).getString("description"),
                                    Parser.parseImagesURL(attractionsJSON.getJSONObject(j).getJSONArray("imagesURL")),
                                    attractionsJSON.getJSONObject(j).getJSONObject("location").getDouble("lat"),
                                    attractionsJSON.getJSONObject(j).getJSONObject("location").getDouble("lng"),
                                    "FAMILY",
                                    "00:00",
                                    "00:00",
//                                    attractionsJSON.getJSONObject(j).getString("type"),
//                                    attractionsJSON.getJSONObject(j).getString("openTime"),
//                                    attractionsJSON.getJSONObject(j).getString("closeTime"),
                                    attractionsJSON.getJSONObject(j).getInt("price")
                            ));
                }
                cities.add(new City(city.getString("_id"),
                        city.getString("name"),
                        city.getString("description"),
                        city.getString("imageURL"),
                        city.getJSONObject("location").getDouble("lat"),
                        city.getJSONObject("location").getDouble("lng"), attractions));

            }
        } catch (JSONException e) {
            return cities;
        }

        return cities;
    }

    public static List<String> parseImagesURL(JSONArray imagesURL) throws JSONException {
        List<String> urls = new ArrayList<>();
        for(int i = 0; i < imagesURL.length(); i++){
            urls.add(imagesURL.getString(i));
        }
        return urls;
    }
}
