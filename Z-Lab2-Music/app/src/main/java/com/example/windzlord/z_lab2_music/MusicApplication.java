package com.example.windzlord.z_lab2_music;

import android.app.Application;

import com.example.windzlord.z_lab2_music.managers.Constant;
import com.example.windzlord.z_lab2_music.managers.NetworkManager;
import com.example.windzlord.z_lab2_music.managers.PreferenceManager;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.Song;
import com.example.windzlord.z_lab2_music.models.json_models.MediaDaddy;
import com.example.windzlord.z_lab2_music.models.json_models.MediaTypeX;
import com.example.windzlord.z_lab2_music.objects.event_bus.AdapterNotifierEvent;
import com.example.windzlord.z_lab2_music.screens.GenresMediaFragment;
import com.example.windzlord.z_lab2_music.screens.GenresSongsFragment;
import com.example.windzlord.z_lab2_music.services.MusicService;
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
        PreferenceManager.init(this);
        NetworkManager.init(this);
        RealmManager.init(this);
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).build());

        if (NetworkManager.getInstance().isConnectedToInternet())
            if (RealmManager.getInstance().getMediaList().isEmpty())
                goMediaType();
//            else goTopSongs();
    }

    private void goMediaType() {
        System.out.println("goMediaType");
        RealmManager.getInstance().clearMedia();

        Retrofit mediaRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.MEDIA_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MusicService musicService = mediaRetrofit.create(MusicService.class);
        musicService.getMediaTypeList().enqueue(new Callback<List<MediaTypeX>>() {

            @Override
            public void onResponse(Call<List<MediaTypeX>> call, Response<List<MediaTypeX>> response) {
                for (MediaTypeX mediaType : response.body()) {
                    if (mediaType.getId().equals(Constant.MUSIC_ID)) {
                        for (MediaTypeX.SubGenre subGenre : mediaType.getSubGenreList()) {
                            RealmManager.getInstance().add(
                                    MediaType.create(subGenre.getId(), subGenre.getTranslationKey())
                            );
                        }
                        EventBus.getDefault().post(new AdapterNotifierEvent(
                                GenresMediaFragment.class.getSimpleName(), -1));
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MediaTypeX>> call, Throwable t) {

            }
        });
    }

    public static void goTopSongs() {
        if (!NetworkManager.getInstance().isConnectedToInternet()) return;
        if (RealmManager.getInstance().getMediaList().isEmpty()) return;
        System.out.println("goTopSongs");
        RealmManager.getInstance().clearSongs();

        Retrofit mediaRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.TOP_SONG_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MusicService musicService = mediaRetrofit.create(MusicService.class);

        int counter = 0;
        for (MediaType media : RealmManager.getInstance().getMediaList()) {
            int position = counter++;

            musicService.getMediaDaddy(media.getId()).enqueue(new Callback<MediaDaddy>() {
                @Override
                public void onResponse(Call<MediaDaddy> call, Response<MediaDaddy> response) {
                    for (MediaDaddy.FeedX.SongX xSong : response.body().getTopSongList()) {
                        RealmManager.getInstance().add(Song.create(media.getId(), xSong));
                    }
                    EventBus.getDefault().post(new AdapterNotifierEvent(
                            GenresSongsFragment.class.getSimpleName(), position));
                    System.out.println(RealmManager.getInstance().getAllSong().size());
                }

                @Override
                public void onFailure(Call<MediaDaddy> call, Throwable t) {

                }
            });
        }
    }

}
