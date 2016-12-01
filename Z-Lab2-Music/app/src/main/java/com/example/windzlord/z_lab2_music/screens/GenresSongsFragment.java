package com.example.windzlord.z_lab2_music.screens;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.z_lab2_music.MusicApplication;
import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.SongAdapter;
import com.example.windzlord.z_lab2_music.managers.PreferenceManager;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.objects.event_bus.AdapterNotifierEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.image_button_like)
    ImageButton imageButtonLike;

    @BindView(R.id.image_button_liked)
    ImageButton imageButtonLiked;

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
        EventBus.getDefault().register(this);

        getContent();
        goTopSongs();
    }

    private void getContent() {
        imageButtonLiked.setVisibility(
                PreferenceManager.getInstance().getFavourite(position) ?
                        View.VISIBLE : View.INVISIBLE);

        MediaType mediaType = RealmManager.getInstance().getMediaList().get(position);
        try {
            InputStream stream = getActivity().getAssets().open("images/genre_" + mediaType.getId() + ".png");
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageMediaType.setImageDrawable(drawable);
        } catch (IOException ex) {

        }
        textMediaType.setText(mediaType.getName().toUpperCase());
    }

    private void goTopSongs() {
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewSongs.setAdapter(new SongAdapter(
                RealmManager.getInstance().getTopSong(
                        RealmManager.getInstance().getMediaList().get(position).getId()
                )));
        recyclerViewSongs.getAdapter().notifyDataSetChanged();
    }

    @Subscribe
    public void updateTopSongs(AdapterNotifierEvent event) {
        if (!this.getClass().getSimpleName().equals(event.getClassName())) return;
        if (this.position == event.getChecker())
            recyclerViewSongs.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.image_button_like)
    public void goLike() {
        imageButtonLiked.setVisibility(View.VISIBLE);
        PreferenceManager.getInstance().putStatusGene(position, true);
    }

    @OnClick(R.id.image_button_liked)
    public void goUnlike() {
        imageButtonLiked.setVisibility(View.INVISIBLE);
        PreferenceManager.getInstance().putStatusGene(position, false);
    }

    @OnClick(R.id.image_button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.image_button_play)
    public void onPlayPressed() {

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
