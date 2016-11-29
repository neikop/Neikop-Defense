package com.example.windzlord.z_lab2_music.models.json_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaDaddy {

    @SerializedName("feed")
    private FeedX mediaFeed;

    public ArrayList<FeedX.SongX> getTopSongList() {
        return mediaFeed.songList;
    }

    public class FeedX {

        @SerializedName("entry")
        private ArrayList<SongX> songList;

        public class SongX {
            @SerializedName("im:name")
            private NameX name;

            public String getName() {
                return name.label;
            }

            @SerializedName("im:artist")
            private ArtistX artist;

            public String getArtist() {
                return artist.label;
            }

            @SerializedName("im:image")
            private ArrayList<ImageX> imageList;

            public ArrayList<ImageX> getImageList() {
                return imageList;
            }

            private class NameX {
                @SerializedName("label")
                private String label;
            }

            private class ArtistX {
                @SerializedName("label")
                private String label;
            }

            public class ImageX {
                @SerializedName("label")
                private String linkImage;

                @SerializedName("attributes")
                private AttributeX attribute;

                public String getLinkImage() {
                    return linkImage;
                }

                public String getHeight() {
                    return attribute.height;
                }

                private class AttributeX {
                    @SerializedName("height")
                    private String height;
                }
            }
        }
    }
}
