package com.example.windzlord.z_lab2_music.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.windzlord.z_lab2_music.MainActivity;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.MediaTypeJSONModel;
import com.example.windzlord.z_lab2_music.recycler_view.MediaAdapter;

import java.util.List;

import retrofit2.*;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaDownloader extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MediaDownloader(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        goMediaType();
    }

    private void goMediaType() {
        Retrofit mediaRetrofit = new Retrofit.Builder()
                .baseUrl("https://rss.itunes.apple.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MediaService mediaService = mediaRetrofit.create(MediaService.class);
        mediaService.getMediaTypeList().enqueue(new Callback<List<MediaTypeJSONModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<MediaTypeJSONModel>> call, Response<List<MediaTypeJSONModel>> response) {
                System.out.println("onResponse");
                MediaAdapter.clear();
                response.body().stream().filter(mediaType -> mediaType.getId().equals("34")).forEach(mediaType -> {
                    mediaType.getSubGenreList().stream().forEach(subGenre ->
                            MediaAdapter.add(subGenre));
                });
                System.out.println(MediaAdapter.getListItems().size());
            }

            @Override
            public void onFailure(Call<List<MediaTypeJSONModel>> call, Throwable t) {

            }
        });
    }
}
