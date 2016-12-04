package com.example.windzlord.z_lab2_music.managers;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class Constant {
    public static String MEDIA_API = "https://rss.iTunes.apple.com";
    public static String MUSIC_ID = "34";

    public static String TITLE = "Explore";
    public static String GENRES = "GENRES";
    public static String PLAYLIST = "PLAYLIST";
    public static String OFFLINE = "OFFLINE";

    public static String TOP_SONG_API = "https://iTunes.apple.com";

    public static String GET_MP3_API = "http://api.mp3.zing.vn";

    public static String toTime(int time) {

        int min = time / 60;
        int sec = time - min * 60;
        return "" + (min < 10 ? ("0" + min) : ("" + min))
                + ":" + (sec < 10 ? ("0" + sec) : ("" + sec));
    }

}
