package com.example.windzlord.z_lab2_music.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.z_lab2_music.objects.event_bus.AdapterNotifier;
import com.example.windzlord.z_lab2_music.objects.event_bus.FragmentChanger;
import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.adapters.MediaAdapter;
import com.example.windzlord.z_lab2_music.managers.listeners.RecyclerViewListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends Fragment {

    @BindView(R.id.recycler_view_media)
    RecyclerView recyclerViewType;

    public GenresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        getContent();
        goMedia();
    }

    private void getContent() {

    }

    private void goMedia() {
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
                SongsFragment songsFragment = new SongsFragment();
                songsFragment.setPosition(position);
                EventBus.getDefault().post(new FragmentChanger(
                        GenresFragment.this.getClass().getSimpleName(), songsFragment, true));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Subscribe
    public void updateMedia(AdapterNotifier event) {
        if (!this.getClass().getSimpleName().equals(event.getClassName())) return;
        recyclerViewType.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
