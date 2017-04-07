package com.example.tallerdyp2.client.Entities;

import java.io.Serializable;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class Review implements Serializable {
    private String name;
    private String comment;
    private int points;

    public Review(String name, String comment, int points) {
        this.name = name;
        this.comment = comment;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
