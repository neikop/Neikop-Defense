package com.example.windzlord.z_lab2_music.models;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaType {

    private String id;
    private String name;

    public MediaType() {
    }

    public MediaType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
