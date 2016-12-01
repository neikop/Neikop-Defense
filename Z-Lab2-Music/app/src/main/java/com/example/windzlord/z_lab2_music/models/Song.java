package com.example.windzlord.z_lab2_music.models;

import com.example.windzlord.z_lab2_music.models.json_models.MediaDaddy;

import io.realm.RealmObject;

/**
 * Created by WindzLord on 12/1/2016.
 */

public class Song extends RealmObject {

    private String mediaID;
    private String name;
    private String artist;
    private String imageSmall;
    private String imageBigger;

    public static Song create(String mediaID, MediaDaddy.FeedX.SongX xSong) {
        Song song = new Song();
        song.mediaID = mediaID;
        song.name = xSong.getName();
        song.artist = xSong.getArtist();
        song.imageSmall = xSong.getImageList().get(1).getLinkImage();
        song.imageBigger = xSong.getImageList().get(2).getLinkImage();
        return song;
    }

    public String getMediaID() {
        return mediaID;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public String getImageBigger() {
        return imageBigger;
    }
}
