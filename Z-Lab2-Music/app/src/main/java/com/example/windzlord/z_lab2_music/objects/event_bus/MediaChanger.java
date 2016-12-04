package com.example.windzlord.z_lab2_music.objects.event_bus;

/**
 * Created by WindzLord on 12/2/2016.
 */

public class MediaChanger {

    private String className;
    private int indexMedia;
    private int indexSong;

    public MediaChanger(String className, int indexMedia, int indexSong) {
        this.className = className;
        this.indexMedia = indexMedia;
        this.indexSong = indexSong;
    }

    public String getClassName() {
        return className;
    }

    public int getIndexMedia() {
        return indexMedia;
    }

    public int getIndexSong() {
        return indexSong;
    }
}
