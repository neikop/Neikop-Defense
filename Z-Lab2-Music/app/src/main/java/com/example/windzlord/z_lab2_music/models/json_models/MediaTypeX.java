package com.example.windzlord.z_lab2_music.models.json_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaTypeX {

    @SerializedName("canBeExplicit")
    private String canBeExplicit;

    @SerializedName("feed_types")
    private ArrayList<FeedType> feedTypeList;

    @SerializedName("id")
    private String id;

    @SerializedName("store")
    private String store;

    @SerializedName("subgenres")
    private ArrayList<SubGenre> subGenreList;

    @SerializedName("translation_key")
    private String translationKey;

    private class FeedType {
        @SerializedName("translation_key")
        private String translationKey;

        @SerializedName("urlPrefix")
        private String urlPrefix;

        @SerializedName("urlSuffix")
        private String urlSuffix;
    }

    public class SubGenre {
        @SerializedName("id")
        private String id;

        @SerializedName("translation_key")
        private String translationKey;

        public String getId() {
            return id;
        }

        public String getTranslationKey() {
            return translationKey;
        }
    }

    public String getId() {
        return id;
    }

    public ArrayList<SubGenre> getSubGenreList() {
        return subGenreList;
    }
}
