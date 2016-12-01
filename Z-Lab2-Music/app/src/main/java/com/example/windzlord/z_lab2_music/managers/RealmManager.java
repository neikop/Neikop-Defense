package com.example.windzlord.z_lab2_music.managers;

import android.app.ActivityManager;
import android.content.Context;

import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.Song;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class RealmManager {

    private static RealmManager instance;

    public static void init(Context context) {
        Realm.init(context);
        instance = new RealmManager();
    }

    public static RealmManager getInstance() {
        return instance;
    }

    public void add(MediaType mediaType) {
        beginTransaction();
        getRealm().copyToRealm(mediaType);
        commitTransaction();
    }

    public void add(Song song) {
        beginTransaction();
        getRealm().copyToRealm(song);
        commitTransaction();
    }

    public List<MediaType> getMediaList() {
        return getRealm().where(MediaType.class).findAll();
    }

    public List<Song> getTopSong(String mediaID) {
        return getRealm().where(Song.class)
                .equalTo("mediaID", mediaID, Case.INSENSITIVE)
                .findAll();
    }

    public List<Song> getAllSong() {
        return getRealm().where(Song.class)
                .findAll();
    }

    public void clearMedia() {
        beginTransaction();
        getRealm().delete(MediaType.class);
        commitTransaction();
    }

    public void clearSongs() {
        beginTransaction();
        getRealm().delete(Song.class);
        commitTransaction();
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    private void beginTransaction() {
        getRealm().beginTransaction();
    }

    private void commitTransaction() {
        getRealm().commitTransaction();
    }
}
