package com.example.windzlord.z_lab2_music.models.json_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WindzLord on 12/2/2016.
 */

public class MediaMommy {

    @SerializedName("numFound")
    private int number;

    @SerializedName("start")
    private int start;

    @SerializedName("docs")
    private ArrayList<SongZing> songZingList;

    public SongZing getFirstSong() {
        for (SongZing song : songZingList) {
            if (song.getDuration() != 0)
                return song;
        }
        return null;
    }

    public class SongZing {

        @SerializedName("song_id")
        private int id;

        @SerializedName("title")
        private String name;

        @SerializedName("artist")
        private String artist;

        @SerializedName("genre")
        private String genre;

        @SerializedName("duration")
        private int duration;

        @SerializedName("source")
        private SourceZing source;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getArtist() {
            return artist;
        }

        public String getGenre() {
            return genre;
        }

        public int getDuration() {
            return duration;
        }

        public String getLink() {
            return source.src128;
        }

        private class SourceZing {

            @SerializedName("128")
            private String src128;

            @SerializedName("320")
            private String src320;

            @SerializedName("lossless")
            private String src500;
        }

    }
}
