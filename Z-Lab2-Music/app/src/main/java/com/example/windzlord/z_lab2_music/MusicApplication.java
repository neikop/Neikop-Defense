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
import com.example.windzlord.z_lab2_music.objects.event_bus.AdapterNotifier;
import com.example.windzlord.z_lab2_music.screens.GenresFragment;
import com.example.windzlord.z_lab2_music.screens.SongsFragment;
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
            else System.out.println("NO INTERNET");
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
                System.out.println("Media response");
                for (MediaTypeX mediaType : response.body()) {
                    if (mediaType.getId().equals(Constant.MUSIC_ID)) {
                        for (MediaTypeX.SubGenre subGenre : mediaType.getSubGenreList()) {
                            RealmManager.getInstance().add(
                                    MediaType.create(subGenre.getId(), subGenre.getTranslationKey())
                            );
                        }
                        EventBus.getDefault().post(new AdapterNotifier(
                                GenresFragment.class.getSimpleName(), -1));
                        goTopSongs();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MediaTypeX>> call, Throwable t) {
                System.out.println("Media failure");
            }
        });
    }

    private void goTopSongs() {
        System.out.println("goTopSongs");
        if (!NetworkManager.getInstance().isConnectedToInternet()) return;
        if (RealmManager.getInstance().getMediaList().isEmpty()) return;
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
                    System.out.println("goTopSongs response " + (position + 1));
                    for (MediaDaddy.FeedX.SongX xSong : response.body().getTopSongList()) {
                        RealmManager.getInstance().add(Song.create(media.getId(), xSong));
                    }
                    EventBus.getDefault().post(new AdapterNotifier(
                            SongsFragment.class.getSimpleName(), position));
                }

                @Override
                public void onFailure(Call<MediaDaddy> call, Throwable t) {
                    System.out.println("goTopSongs failure");
                }
            });
        }
    }

}
