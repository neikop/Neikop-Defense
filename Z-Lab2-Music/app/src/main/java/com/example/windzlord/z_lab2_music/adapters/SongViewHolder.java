package com.example.windzlord.z_lab2_music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.models.json_models.MediaDaddy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class SongViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_song_small)
    ImageView imageSongSmall;

    @BindView(R.id.text_song_name)
    TextView textSongName;

    @BindView(R.id.text_song_artist)
    TextView textSongArtist;

    public SongViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(MediaDaddy.FeedX.SongX song) {
        textSongName.setText(song.getName());
        textSongArtist.setText(song.getArtist());
    }
}
