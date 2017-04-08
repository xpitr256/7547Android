package com.example.tallerdyp2.client.Entities;

import java.io.Serializable;

/**
 * Created by Sebastian on 6/4/2017.
 */

public class Review implements Serializable {


    private String userAvatarUrl;
    private String userName;
    private String userId;
    private String comment;
    private double rating;

    public Review(String userName, String userId, String userAvatarUrl, String comments, double rating) {
        this.userName = userName;
        this.userId = userId;
        this.userAvatarUrl = userAvatarUrl;
        this.comment = comments;
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
