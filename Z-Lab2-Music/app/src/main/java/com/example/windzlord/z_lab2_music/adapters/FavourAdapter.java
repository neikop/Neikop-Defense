package com.example.windzlord.z_lab2_music.adapters;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.views_holder.FavourViewHolder;
import com.example.windzlord.z_lab2_music.managers.RealmManager;

import java.util.ArrayList;

/**
 * Created by WindzLord on 12/1/2016.
 */

public class FavourAdapter extends RecyclerView.Adapter<FavourViewHolder> {

    private static ArrayList<Integer> favourList = new ArrayList<>();

    public static ArrayList<Integer> getFavourList() {
        return favourList;
    }

    public static void clearFavourList() {
        favourList.clear();
    }

    @Override
    public FavourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_favour, parent, false);
        return new FavourViewHolder(parent.getContext(), itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(FavourViewHolder holder, int position) {
        holder.bind(RealmManager.getInstance()
                .getMediaList().get(favourList.get(position)));
    }

    @Override
    public int getItemCount() {
        return favourList.size();
    }
}