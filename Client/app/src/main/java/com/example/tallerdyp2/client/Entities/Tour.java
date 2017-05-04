package com.example.tallerdyp2.client.Entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sebastian on 29/4/2017.
 */

public class Tour implements Serializable{

    private String id;
    private String name;
    private String description;
    private List<Attraction> attractions;

    public Tour(String id, String name, String description, List<Attraction> attractions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.attractions = attractions;
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

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
    }
}
