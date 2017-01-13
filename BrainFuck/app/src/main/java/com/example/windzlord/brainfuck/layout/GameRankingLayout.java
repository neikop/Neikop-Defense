package com.example.windzlord.brainfuck.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WindzLord on 1/12/2017.
 */

public class GameRankingLayout extends FrameLayout {

    @BindView(R.id.textView_number)
    TextView textViewNumber;

    @BindView(R.id.textView_name)
    TextView textViewName;

    @BindView(R.id.textView_score)
    TextView textViewScore;

    @BindView(R.id.imageView_avatar)
    ImageView imageViewAvatar;

    private int number;
    private Drawable image;
    private Drawable background;
    private String name;
    private int score;

    public GameRankingLayout(Context context) {
        super(context);
        initFromContext(context, null);
    }

    public GameRankingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFromContext(context, attrs);
    }

    public GameRankingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromContext(context, attrs);
    }

    private void initFromContext(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.custom_game_layout_ranking, this);
        ButterKnife.bind(this, view);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/BreeSerif.otf");
        textViewName.setTypeface(font);
        getValues(context.obtainStyledAttributes(attrs, R.styleable.GameRankingLayout));
    }

    private void getValues(TypedArray typedArray) {
        number = typedArray.getInt(R.styleable.GameRankingLayout_number, -1);
        name = typedArray.getString(R.styleable.GameRankingLayout_name_ranking);
        if (name == null) name = "Unknown";
        score = typedArray.getInt(R.styleable.GameRankingLayout_score_ranking, 0);
        image = typedArray.getDrawable(R.styleable.GameRankingLayout_src_ranking);
        if (image == null) image = getResources().getDrawable(R.drawable.z_character_guest);
        background = typedArray.getDrawable(R.styleable.GameRankingLayout_bgr_ranking);
        if (background == null) background = getResources().getDrawable(
                R.drawable.custom_oval_background_outline_profile);
        typedArray.recycle();
        updateValues();
    }

    private void updateValues() {
        textViewNumber.setText(number + "");
        imageViewAvatar.setImageDrawable(image);
        imageViewAvatar.setBackgroundDrawable(background);
        textViewName.setText(name);
        textViewScore.setText("Neuron " + score);
    }

    public GameRankingLayout setNumber(int number) {
        this.number = number;
        updateValues();
        return this;
    }

    public GameRankingLayout setImage(Drawable image) {
        this.image = image;
        updateValues();
        return this;
    }

    public GameRankingLayout setImageBackground(Drawable background) {
        this.background = background;
        updateValues();
        return this;
    }

    public GameRankingLayout setName(String name) {
        this.name = name;
        updateValues();
        return this;
    }

    public GameRankingLayout setScore(int score) {
        this.score = score;
        updateValues();
        return this;
    }
}
