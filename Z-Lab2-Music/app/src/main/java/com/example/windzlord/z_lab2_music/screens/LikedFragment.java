package com.example.windzlord.z_lab2_music.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.objects.event_bus.FragmentChanger;
import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.FavourAdapter;
import com.example.windzlord.z_lab2_music.managers.PreferenceManager;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.managers.listeners.RecyclerViewListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikedFragment extends Fragment {

    @BindView(R.id.recycler_view_favour)
    RecyclerView recyclerViewFavour;

    public LikedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_havour, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getContent();
        goFavourite();
    }

    private void getContent() {

    }

    private void goFavourite() {
        FavourAdapter.clearFavourList();

        for (int i = 0; i < RealmManager.getInstance().getMediaList().size(); i++) {
            if (PreferenceManager.getInstance().getFavourite(i)) {
                FavourAdapter.getFavourList().add(i);
            }
        }
        recyclerViewFavour.setLayoutManager(new GridLayoutManager(
                getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        recyclerViewFavour.setAdapter(new FavourAdapter());
        recyclerViewFavour.getAdapter().notifyDataSetChanged();

        recyclerViewFavour.addOnItemTouchListener(new RecyclerViewListener(
                getActivity(), recyclerViewFavour, new RecyclerViewListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SongsFragment songsFragment = new SongsFragment();
                songsFragment.setPosition(FavourAdapter.getFavourList().get(position));
                EventBus.getDefault().post(new FragmentChanger(
                        LikedFragment.this.getClass().getSimpleName(), songsFragment, true));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

}
