package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.File;

/**
 * Created by tungb on 10/14/2016.
 */

public class FileManager {
    private static final String TAG = FileManager.class.getSimpleName();
    private Storage storage;
    private static final String IMAGE_DIR = "images";
    private static final String IMAGE_TYPE = ".png";

    private FileManager(Context context) {
        storage = SimpleStorage.getInternalStorage(context);
    }

    private static FileManager instance;

    public static FileManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new FileManager(context);
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
