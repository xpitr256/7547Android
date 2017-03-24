package com.example.tallerdyp2.client.Entities;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sebastian on 23/3/2017.
 */

public class Attraction implements Serializable{

    private String id;
    private String name;
    private String description;
    private String imageURL;
    private double latitude;
    private double longitude;
    private TypeAttraction type;
    private Date openTime;
    private Date closeTime;
    private int price;

    public Attraction(String id, String name, String description, String imageURL, double latitude, double longitude, String type, String openTime, String closeTime, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = TypeAttraction.fromString(type);
        try {
            this.openTime = new SimpleDateFormat("HH:mm").parse(openTime);
            this.closeTime = new SimpleDateFormat("HH:mm").parse(closeTime);
        } catch (ParseException e) {
            this.openTime = Time.valueOf("00:00");
            this.closeTime = Time.valueOf("00:00");
        }
        this.price = price;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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
