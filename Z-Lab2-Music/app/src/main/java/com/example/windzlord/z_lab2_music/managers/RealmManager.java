package com.example.windzlord.z_lab2_music.managers;

import android.app.ActivityManager;
import android.content.Context;

import com.example.windzlord.z_lab2_music.models.MediaType;

import java.util.List;

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

    public List<MediaType> getMediaList() {
        return getRealm().where(MediaType.class).findAll();
    }

    public void clear() {
        beginTransaction();
        getRealm().delete(MediaType.class);
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
