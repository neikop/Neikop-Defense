package com.example.windzlord.z_lab2_music;

import android.app.Application;

import com.example.windzlord.z_lab2_music.managers.Constant;
import com.example.windzlord.z_lab2_music.managers.NetworkManager;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.json_models.MediaTypeX;
import com.example.windzlord.z_lab2_music.services.MediaService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by WindzLord on 11/28/2016.
 */

public class MusicApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        settingThingsUp();
    }

    private void settingThingsUp() {
        NetworkManager.init(this);
        RealmManager.init(this);
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).build());

        if (NetworkManager.getInstance().isConnectedToInternet())
            if (RealmManager.getInstance().getMediaList().isEmpty())
                goMediaType();

    }

    private void goMediaType() {
        Retrofit mediaRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.MEDIA_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MediaService mediaService = mediaRetrofit.create(MediaService.class);
        mediaService.getMediaTypeList().enqueue(new Callback<List<MediaTypeX>>() {

            @Override
            public void onResponse(Call<List<MediaTypeX>> call, Response<List<MediaTypeX>> response) {
                for (MediaTypeX mediaType : response.body()) {
                    if (mediaType.getId().equals(Constant.MUSIC_ID)) {
                        for (MediaTypeX.SubGenre subGenre : mediaType.getSubGenreList()) {
                            RealmManager.getInstance().add(
                                    MediaType.create(subGenre.getId(), subGenre.getTranslationKey())
                            );
                        }
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MediaTypeX>> call, Throwable t) {

            }
        });
    }
}
