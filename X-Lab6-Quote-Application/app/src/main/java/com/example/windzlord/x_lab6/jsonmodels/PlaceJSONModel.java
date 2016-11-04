package com.example.windzlord.x_lab6.jsonmodels;

/**
 * Created by WindzLord on 10/13/2016.
 */

public class PlaceJSONModel {

    private String userId;
    private String id;
    private String title;
    private String body;

    public PlaceJSONModel(String userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
