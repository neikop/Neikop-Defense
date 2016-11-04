package com.example.windzlord.x_lab6.managers;

import android.content.Context;
import android.graphics.Bitmap;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.File;

/**
 * Created by WindzLord on 10/22/2016.
 */

public class FileManager {
    private Storage storage;
    private static final String IMAGE_DIRECTORY = "images";
    public static final String IMAGE_FILE_NAME = "unplashImage";

    private FileManager(Context context) {
        storage = SimpleStorage.getInternalStorage(context);
    }

    public void createImage(Bitmap bitmap, String fileName) {
        storage.createFile(IMAGE_DIRECTORY, fileName, bitmap);
    }

    public void createImage(Bitmap bitmap, int index) {
        createImage(bitmap, IMAGE_FILE_NAME + index);
    }

    public File loadImageFile(String fileName) {
        return storage.getFile(IMAGE_DIRECTORY, fileName);
    }

    private static FileManager instance;

    public static FileManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new FileManager(context);
    }
}
