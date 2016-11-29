package com.example.windzlord.z_lab2_music.recycler_view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.MediaTypeJSONModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WindzLord on 11/29/2016.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaViewHolder> {

    private Context context;

    public MediaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.genres_item, parent, false);

        MediaViewHolder itemViewHolder = new MediaViewHolder(context, itemView);
        return itemViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MediaViewHolder holder, int position) {
        holder.bind(listItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    private static List<MediaType> listItems = new ArrayList<>();

    public static List<MediaType> getListItems() {
        return listItems;
    }

    public static void add(MediaTypeJSONModel.SubGenre subGenre) {
        listItems.add(new MediaType(subGenre.getId(), subGenre.getTranslationKey()));
    }

    public static void clear() {
        listItems.clear();
    }
}

