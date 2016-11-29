package com.example.windzlord.z_lab2_music.screens;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.SongAdapter;
import com.example.windzlord.z_lab2_music.managers.Constant;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.json_models.MediaDaddy;
import com.example.windzlord.z_lab2_music.services.MediaService;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresSongsFragment extends Fragment {

    @BindView(R.id.image_media_type)
    ImageView imageMediaType;

    @BindView(R.id.text_media_type)
    TextView textMediaType;

    @BindView(R.id.recycler_view_songs)
    RecyclerView recyclerViewSongs;

    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public GenresSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres_songs, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getContent();
        goTopSongs();
    }

    private void getContent() {
        MediaType mediaType = RealmManager.getInstance().getMediaList().get(position);
        try {
            InputStream stream = getActivity().getAssets().open("images/genre_" + mediaType.getId() + ".png");
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageMediaType.setImageDrawable(drawable);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        textMediaType.setText(mediaType.getName().toUpperCase());
    }

    private void goTopSongs() {
        Retrofit mediaRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.TOP_SONG_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MediaService mediaService = mediaRetrofit.create(MediaService.class);
        mediaService.getMediaDaddy(RealmManager.getInstance()
                .getMediaList().get(position).getId()
        ).enqueue(new Callback<MediaDaddy>() {
            @Override
            public void onResponse(Call<MediaDaddy> call, Response<MediaDaddy> response) {

                LinearLayoutManager manager = new LinearLayoutManager(
                        getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerViewSongs.setLayoutManager(manager);

                SongAdapter songAdapter = new SongAdapter(response.body().getTopSongList());
                recyclerViewSongs.setAdapter(songAdapter);

                getActivity().runOnUiThread(songAdapter::notifyDataSetChanged);
            }

            @Override
            public void onFailure(Call<MediaDaddy> call, Throwable t) {

            }
        });
    }

}
