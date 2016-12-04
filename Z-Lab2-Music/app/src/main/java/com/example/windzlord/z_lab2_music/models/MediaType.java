package com.example.windzlord.z_lab2_music.models;

import io.realm.RealmObject;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaType extends RealmObject {

    private int index;
    private String id;
    private String name;

    public static MediaType create(String id, String name) {
        MediaType media = new MediaType();
        media.id = id;
        media.name = name;
        media.index = -1;
        return media;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
