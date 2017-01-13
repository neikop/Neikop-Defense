package com.example.windzlord.brainfuck.objects;

/**
 * Created by WindzLord on 1/13/2017.
 */

public class MessageManager {

    private String title;
    private String content;

    public MessageManager(String title, String content) {
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
