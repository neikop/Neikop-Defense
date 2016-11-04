package com.example.windzlord.x_session7.recycler_view;

import com.example.windzlord.x_session7.models.PostJSONModel;

import java.util.ArrayList;

/**
 * Created by WindzLord on 10/23/2016.
 */

public class Post {
    private String title;
    private String content;

    public Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(PostJSONModel postJSONModel) {
        this.title = postJSONModel.getTitle();
        this.content = postJSONModel.getContent();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static final ArrayList<Post> LIST = new ArrayList<>();
}
