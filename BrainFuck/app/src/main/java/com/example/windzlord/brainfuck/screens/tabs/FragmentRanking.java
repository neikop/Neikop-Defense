package com.example.windzlord.brainfuck.screens.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.layout.GameRankingLayout;
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
    }

    private void getPlayerRanking() {
        List<HighScore> listPlayer = ManagerUserData.getInstance().getListPlayer();
        for (HighScore player : listPlayer)
            player.setHighscore(ManagerUserData.getInstance().getExperience(player.getUserId()));

        Collections.sort(listPlayer, (player1, player2) -> player2.getHighscore() == player1.getHighscore() ?
                player2.getUserName().compareTo(player1.getUserName()) : player2.getHighscore() - player1.getHighscore());
        for (int i = 0; i < listPlayer.size(); i++) {
            ((GameRankingLayout) layoutPlayerRanking.getChildAt(i))
                    .setName(listPlayer.get(i).getUserName()
                            .substring(2, listPlayer.get(i).getUserName().length() - 1))
                    .setScore(listPlayer.get(i).getHighscore());
        }
    }
}
