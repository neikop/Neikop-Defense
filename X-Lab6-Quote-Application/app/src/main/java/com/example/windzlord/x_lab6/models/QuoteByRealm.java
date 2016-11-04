package com.example.windzlord.x_lab6.models;

import io.realm.RealmObject;

/**
 * Created by WindzLord on 11/4/2016.
 */

public class QuoteByRealm extends RealmObject {
    private String title;
    private String content;

    public static QuoteByRealm create(String title, String content) {
        QuoteByRealm quote = new QuoteByRealm();
        quote.title = title;
        quote.content = content;
        return quote;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "QuoteByRealm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
