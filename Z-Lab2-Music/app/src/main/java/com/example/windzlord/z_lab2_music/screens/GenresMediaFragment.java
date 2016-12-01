package com.example.windzlord.z_lab2_music.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.FragmentEvent;
import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.MediaAdapter;
import com.example.windzlord.z_lab2_music.managers.listeners.RecyclerViewListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenresMediaFragment extends Fragment {

    @BindView(R.id.recycler_view_type)
    RecyclerView recyclerViewType;

    public GenresMediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres_pager, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getContent();
    }

    private void getContent() {
        GridLayoutManager manager = new GridLayoutManager(
                getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % 3 == 0 ? 2 : 1;
            }
        });
        recyclerViewType.setLayoutManager(manager);
        recyclerViewType.setAdapter(new MediaAdapter());

        recyclerViewType.addOnItemTouchListener(new RecyclerViewListener(
                getActivity(), recyclerViewType, new RecyclerViewListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GenresSongsFragment songsFragment = new GenresSongsFragment();
                songsFragment.setPosition(position);
                EventBus.getDefault().post(new FragmentEvent(songsFragment, true, 1));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

}
