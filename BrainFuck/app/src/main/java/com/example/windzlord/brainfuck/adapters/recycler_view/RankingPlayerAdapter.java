package com.example.windzlord.brainfuck.adapters.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.objects.models.HighScore;

import java.util.List;

/**
 * Created by WindzLord on 1/15/2017.
 */

public class RankingPlayerAdapter extends RecyclerView.Adapter<RankingPlayerHolder> {

    private Context context;
    private List<HighScore> adapter;

    public RankingPlayerAdapter(Context context, List<HighScore> adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public RankingPlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_game_layout_ranking, parent, false);
        return new RankingPlayerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingPlayerHolder holder, int position) {
        holder.bind(context, position + 1, adapter.get(position));
    }

    @Override
    public int getItemCount() {
        return adapter.size();
    }
}
