package com.example.windzlord.z_lab2_music.adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.views_holder.MediaViewHolder;
import com.example.windzlord.z_lab2_music.managers.RealmManager;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_genre, parent, false);
        return new MediaViewHolder(parent.getContext(), itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        holder.bind(RealmManager.getInstance().getMediaList().get(position));
    }

    @Override
    public int getItemCount() {
        return RealmManager.getInstance().getMediaList().size();
    }
}

