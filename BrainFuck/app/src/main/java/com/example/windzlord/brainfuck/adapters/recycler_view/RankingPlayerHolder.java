package com.example.windzlord.brainfuck.adapters.recycler_view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.objects.models.HighScore;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WindzLord on 1/15/2017.
 */

public class RankingPlayerHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView_number_ranking)
    TextView textViewNumber;

    @BindView(R.id.textView_name_ranking)
    TextView textViewName;

    @BindView(R.id.textView_score_ranking)
    TextView textViewScore;

    @BindView(R.id.imageView_avatar_ranking)
    ImageView imageViewAvatar;

    @BindView(R.id.layout_background_ranking)
    View viewBackground;

    @BindView(R.id.layout_background_listener)
    View viewListener;

    public RankingPlayerHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Context context, int number, HighScore player) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/BreeSerif.otf");
        textViewName.setTypeface(font);
        textViewNumber.setText("" + number);
        textViewName.setText(player.getUserName().substring(2, player.getUserName().length() - 1));
        textViewScore.setText("Neuron " + player.getScore());
        switch (number) {
            case 1:
                imageViewAvatar.setImageResource(R.drawable.ic_ranking_diamond);
                break;
            case 2:
                imageViewAvatar.setImageResource(R.drawable.ic_ranking_premium);
                break;
            case 3:
                imageViewAvatar.setImageResource(R.drawable.ic_ranking_gold);
                break;
            case 4:
                imageViewAvatar.setImageResource(R.drawable.ic_ranking_silver);
                break;
            default:
                imageViewAvatar.setImageResource(R.drawable.ic_ranking_bronze);
        }
        boolean chosen = ManagerPreference.getInstance().getUserID().equals(player.getUserId());
        viewBackground.setBackgroundColor(
                chosen ? Color.parseColor("#FCFF00") : Color.parseColor("#CCAAEE"));
        textViewScore.setTextColor(
                chosen ? Color.parseColor("#123456") : Color.parseColor("#696969"));
    }

}
