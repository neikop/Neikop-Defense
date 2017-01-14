package com.example.windzlord.brainfuck.screens.tabs;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.layout.GameRankingLayout;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.ManagerUserData;
import com.example.windzlord.brainfuck.objects.models.HighScore;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRanking extends Fragment {

    @BindView(R.id.layout_player_ranking)
    ViewGroup layoutPlayerRanking;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.frameLayout_ranking_god)
    View layoutRankingGod;

    @BindView(R.id.frameLayout_ranking_player)
    View layoutRankingPlayer;

    private List<HighScore> players;

    public FragmentRanking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment_ranking, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getPlayerRanking();
        addListeners();
    }

    private void getPlayerRanking() {
        players = ManagerUserData.getInstance().getListPlayer();
        for (HighScore player : players)
            player.setScore(ManagerUserData.getInstance().getExperience(player.getUserId()));

        Collections.sort(players, (o, s) -> s.getScore() == o.getScore() ?
                s.getUserName().compareTo(o.getUserName()) : s.getScore() - o.getScore());

        for (int i = 0; i < (players.size() <= 10 ? players.size() : 10); i++) {
            boolean chosen = ManagerPreference.getInstance().getUserID()
                    .equals(players.get(i).getUserId());
            GameRankingLayout layout = ((GameRankingLayout) layoutPlayerRanking.getChildAt(i));
            layout.setName(players.get(i).getUserName()
                    .substring(2, players.get(i).getUserName().length() - 1));
            layout.setScore(players.get(i).getScore());
            layout.setBgrColor(chosen ? Color.parseColor("#FCFF00") : Color.parseColor("#CCAAEE"));
        }
    }

    private void addListeners() {
        layoutRankingGod.setOnClickListener((ignored) -> System.out.println("CLICKED NULL"));

        for (int i = 0; i < layoutPlayerRanking.getChildCount(); i++) {
            int x = i;
            ((GameRankingLayout) layoutPlayerRanking.getChildAt(i)) // DO NOT DELETE
                    .setOnClickListener((v) -> {
                        if (layoutRankingGod.getVisibility() == View.VISIBLE) {
                            getChildFragmentManager().popBackStack();
                            layoutRankingGod.setVisibility(View.INVISIBLE);
                        } else {
                            FragmentRankingPlayer player = new FragmentRankingPlayer();
                            player.setUserID(players.get(x).getUserId());
                            getChildFragmentManager()
                                    .beginTransaction()
                                    .setCustomAnimations(R.anim.go_fade_in_300, R.anim.nothing)
                                    .replace(R.id.frameLayout_ranking_player, player)
                                    .addToBackStack(null)
                                    .commit();
                            layoutRankingGod.setVisibility(View.VISIBLE);

                        }
                    });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getChildFragmentManager().popBackStack();
    }
}
