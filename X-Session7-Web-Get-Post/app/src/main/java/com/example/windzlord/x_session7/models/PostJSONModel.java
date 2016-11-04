package com.example.windzlord.x_session7.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WindzLord on 10/23/2016.
 */

public class PostJSONModel {
    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    public PostJSONModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
