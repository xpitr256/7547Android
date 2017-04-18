package com.example.tallerdyp2.client.Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sebastian on 14/4/2017.
 */

public class PointOfInterest implements Serializable{

    private String audioURL;
    private String id;
    private String name;
    private String description;
    private List<String> imagesURL;

    public PointOfInterest(String id, String name, String description, List<String> imagesURL, String audioURL) {
        this.audioURL = audioURL;
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagesURL = imagesURL;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
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
}
