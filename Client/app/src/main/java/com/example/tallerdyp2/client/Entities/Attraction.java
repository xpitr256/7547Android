package com.example.tallerdyp2.client.Entities;

import com.example.tallerdyp2.client.utils.Helper;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sebastian on 23/3/2017.
 */

public class Attraction implements Serializable{

    private double rating;
    private List<Review> reviews;
    private String audioURL;
    private String id;
    private String name;
    private String description;
    private List<String> imagesURL;
    private double latitude;
    private double longitude;
    private TypeAttraction type;
    private Date openTime;
    private Date closeTime;
    private int price;
    private double distance;

    public Attraction(String id, String name, String description, double rating, List<String> imagesURL, String audioURL, double latitude, double longitude, String type, String openTime, String closeTime, int price, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.imagesURL = imagesURL;
        this.audioURL = audioURL;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = TypeAttraction.fromString(type);

        try {
            this.openTime = new SimpleDateFormat("HH:mm").parse(openTime);
        } catch (ParseException|NullPointerException e) {
            this.openTime = Time.valueOf("00:00:00");
        }

        try {
            this.closeTime = new SimpleDateFormat("HH:mm").parse(closeTime);
        } catch (ParseException|NullPointerException e) {
            this.closeTime = Time.valueOf("00:00:00");
        }

        this.price = price;
        this.reviews = reviews;
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

    public TypeAttraction getType() {
        return type;
    }

    public void setType(TypeAttraction type) {
        this.type = type;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public void updateDistance(double lat, double lng) {
        this.distance = Helper.distance(this.latitude, lat, this.longitude, lng, 0.0, 0.0);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public enum TypeAttraction {
        FAMILY("FAMILY"),
        KIDS("KIDS"),
        ADVENTURE("ADVENTURE"),
        D("text4");

        private String text;

        TypeAttraction(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }

        public static TypeAttraction fromString(String text) {
            for (TypeAttraction type : TypeAttraction.values()) {
                if (type.text.equalsIgnoreCase(text)) {
                    return type;
                }
            }
            return FAMILY;
        }
    }
}
