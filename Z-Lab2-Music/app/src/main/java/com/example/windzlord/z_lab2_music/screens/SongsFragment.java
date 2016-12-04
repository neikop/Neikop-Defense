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

import com.example.windzlord.z_lab2_music.MainActivity;
import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.SongAdapter;
import com.example.windzlord.z_lab2_music.managers.PreferenceManager;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.managers.listeners.RecyclerViewListener;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.objects.event_bus.AdapterNotifier;
import com.example.windzlord.z_lab2_music.objects.event_bus.MediaChanger;

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
public class SongsFragment extends Fragment {

    @BindView(R.id.image_button_liked)
    ImageButton buttonLiked;

    @BindView(R.id.image_media_type)
    ImageView imageMediaType;

    @BindView(R.id.text_media_type)
    TextView textMediaType;

    @BindView(R.id.recycler_view_songs)
    RecyclerView recyclerViewSongs;

    private MediaType media;

    public void setPosition(int position) {
        media = RealmManager.getInstance().getMediaList().get(position);
        RealmManager.getInstance().setMediaIndex(media, position);
    }

    public SongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
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
        buttonLiked.setVisibility(
                PreferenceManager.getInstance().getFavourite(media.getIndex()) ?
                        View.VISIBLE : View.INVISIBLE);

        textMediaType.setText(media.getName().toUpperCase());
        try {
            InputStream stream = getActivity().getAssets().open("images/genre_" + media.getId() + ".png");
            Drawable drawable = Drawable.createFromStream(stream, null);
            imageMediaType.setImageDrawable(drawable);
        } catch (IOException ex) {

        }
    }

    private void goTopSongs() {
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewSongs.setAdapter(new SongAdapter(
                RealmManager.getInstance().getTopSong(media.getId())));
        recyclerViewSongs.getAdapter().notifyDataSetChanged();

        recyclerViewSongs.addOnItemTouchListener(new RecyclerViewListener(
                getActivity(), recyclerViewSongs, new RecyclerViewListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new MediaChanger(
                        MainActivity.class.getSimpleName(), media.getIndex(), position));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Subscribe
    public void updateTopSongs(AdapterNotifier event) {
        if (!this.getClass().getSimpleName().equals(event.getClassName())) return;
        if (media.getIndex() == event.getChecker())
            recyclerViewSongs.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.image_button_like)
    public void goLike() {
        buttonLiked.setVisibility(View.VISIBLE);
        PreferenceManager.getInstance().putStatusGene(media.getIndex(), true);
    }

    @OnClick(R.id.image_button_liked)
    public void goUnlike() {
        buttonLiked.setVisibility(View.INVISIBLE);
        PreferenceManager.getInstance().putStatusGene(media.getIndex(), false);
    }

    @OnClick(R.id.image_button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.button_play)
    public void onPlayPressed() {
        EventBus.getDefault().post(new MediaChanger(
                MainActivity.class.getSimpleName(), media.getIndex(), 0));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
