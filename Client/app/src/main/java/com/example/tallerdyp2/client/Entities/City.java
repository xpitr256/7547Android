package com.example.tallerdyp2.client.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebastian on 18/3/2017.
 */

public class City implements Serializable {

    private String id;
    private String name;
    private String description;
    private List<String> imagesURL;
    private double latitude;
    private double longitude;
    private List<Attraction> attractions;
    private List<Travel> travels;

    public City(String id, String name, String description, List<String> imagesURL, double latitude, double longitude, List<Attraction> attractions, List<Travel> travels) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.imagesURL = imagesURL;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attractions = attractions != null ? attractions : new ArrayList<Attraction>();
        this.travels = travels != null ? travels : new ArrayList<Travel>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }


}

