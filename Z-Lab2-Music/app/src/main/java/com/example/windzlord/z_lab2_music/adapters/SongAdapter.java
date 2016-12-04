package com.example.windzlord.z_lab2_music.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.views_holder.SongViewHolder;
import com.example.windzlord.z_lab2_music.models.Song;

import java.util.List;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {


    private List<Song> topSongList;

    public SongAdapter(List<Song> topSongList) {
        this.topSongList = topSongList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.bind(topSongList.get(position));
    }

    @Override
    public int getItemCount() {
        return topSongList.size();
    }
}
