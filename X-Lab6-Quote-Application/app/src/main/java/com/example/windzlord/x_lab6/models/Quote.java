package com.example.windzlord.x_lab6.models;

import com.example.windzlord.x_lab6.jsonmodels.QuoteJSONModel;

/**
 * Created by WindzLord on 10/26/2016.
 */

public class Quote {

    private int id;
    private String title;
    private String content;

    public Quote(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Quote(String title, String content) {
        this(-1, title, content);
    }

    public Quote(QuoteJSONModel quoteJSONModel) {
        this(quoteJSONModel.getTitle(), quoteJSONModel.getContent());
    }

    public Quote(QuoteByRealm quoteByRealm) {
        this(quoteByRealm.getTitle(), quoteByRealm.getContent());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", id, title, content);
    }
}
