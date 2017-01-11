package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.File;

public class ManagerFile {

    private static final String TAG = ManagerFile.class.getSimpleName();
    private Storage storage;
    private static final String IMAGE_DIR = "images";
    private static final String IMAGE_TYPE = ".png";

    private ManagerFile(Context context) {
        storage = SimpleStorage.getInternalStorage(context);
    }

    private static ManagerFile instance;

    public static ManagerFile getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerFile(context);
    }

    public void createImage(Bitmap bitmap, String userId) {
        if (bitmap == null)
            Log.d(TAG, "bitmap null");
        storage.createFile(IMAGE_DIR, userId + IMAGE_TYPE, bitmap);
    }

    public File loadImage(String userId) {
        return storage.getFile(IMAGE_DIR, userId + IMAGE_TYPE);
    }


}
