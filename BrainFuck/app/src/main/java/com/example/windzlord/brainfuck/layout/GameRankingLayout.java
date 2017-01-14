package com.example.windzlord.brainfuck.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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

    @BindView(R.id.layout_background_ranking)
    View viewBackground;

    private int number;
    private Drawable image;
    private int bgrColor;
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
        bgrColor = typedArray.getInt(R.styleable.GameRankingLayout_bgr_ranking,
                Color.parseColor("#CCAAEE"));
        typedArray.recycle();
        updateValues();
    }

    private void updateValues() {
        textViewNumber.setText(number + "");
        imageViewAvatar.setImageDrawable(image);
        viewBackground.setBackgroundColor(bgrColor);
        textViewName.setText(name);
        textViewScore.setText("Neuron " + score);
    }

    public void setNumber(int number) {
        this.number = number;
        updateValues();
    }

    public void setImage(Drawable image) {
        this.image = image;
        updateValues();
    }

    public void setBgrColor(int bgrColor) {
        this.bgrColor = bgrColor;
        updateValues();
    }

    public void setName(String name) {
        this.name = name;
        updateValues();
    }

    public void setScore(int score) {
        this.score = score;
        updateValues();
    }
}
