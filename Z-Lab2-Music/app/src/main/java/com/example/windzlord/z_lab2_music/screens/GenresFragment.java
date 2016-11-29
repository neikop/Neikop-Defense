package com.example.windzlord.z_lab2_music.screens;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.recycler_view.MediaAdapter;
import com.example.windzlord.z_lab2_music.services.MediaDownloader;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public GenresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager manager = new GridLayoutManager(
                getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MediaAdapter(getActivity()));

        recyclerView.getAdapter().notifyDataSetChanged();

        return view;
    }

}
