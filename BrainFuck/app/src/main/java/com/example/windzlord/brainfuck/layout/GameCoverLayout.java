package com.example.windzlord.brainfuck.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.managers.ManagerBrain;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WindzLord on 12/27/2016.
 */

public class GameCoverLayout extends FrameLayout {

    @BindView(R.id.imageView_game)
    ImageView imageViewGame;

    @BindView(R.id.textView_game)
    TextView textViewGame;

    @BindView(R.id.textView_level)
    TextView textViewLevel;

    @BindView(R.id.progressBar_level)
    ProgressBar progressBar_level;

    @BindView(R.id.textView_exp_current)
    TextView textViewCurrentExp;

    @BindView(R.id.textView_exp_nextlvl)
    TextView textViewNextLvlExp;

    @BindView(R.id.unlocked_layout)
    RelativeLayout layoutUnlocked;

    @BindView(R.id.locked_layout)
    RelativeLayout layoutLocked;

    @BindView(R.id.textView_2score)
    TextView textViewHiScore;

    private String name;
    private boolean unlocked;
    private Drawable image;
    private int level;
    private int expCurrent;
    private int expNextLvl;
    private int score;

    public GameCoverLayout(Context context) {
        super(context);
        initFromContext(context, null);
    }

    public GameCoverLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFromContext(context, attrs);
    }

    public GameCoverLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromContext(context, attrs);
    }

    private void initFromContext(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.custom_game_layout_cover, this);
        ButterKnife.bind(this, view);
        getValues(context.obtainStyledAttributes(attrs, R.styleable.GameCoverLayout));
    }

    private void getValues(TypedArray typedArray) {
        name = typedArray.getString(R.styleable.GameCoverLayout_name);
        image = typedArray.getDrawable(R.styleable.GameCoverLayout_src);
        if (image == null) image = getResources().getDrawable(R.drawable.ic_launcher_ok);
        level = typedArray.getInteger(R.styleable.GameCoverLayout_level, 1);
        expCurrent = typedArray.getInteger(R.styleable.GameCoverLayout_exp_current, 0);
        expNextLvl = typedArray.getInteger(R.styleable.GameCoverLayout_exp_nextlvl, 300);
        score = typedArray.getInteger(R.styleable.GameCoverLayout_score, 0);
        unlocked = typedArray.getBoolean(R.styleable.GameCoverLayout_unlocked, false);
        typedArray.recycle();
        updateValues();
    }

    private void updateValues() {
        layoutUnlocked.setVisibility(unlocked ? VISIBLE : GONE);
        layoutLocked.setVisibility(unlocked ? GONE : VISIBLE);
        imageViewGame.setImageDrawable(unlocked ?
                image : getResources().getDrawable(R.drawable.ic_launcher_locked));
        textViewGame.setText(name);
        textViewLevel.setText("Exp. Lv. " + level);
        progressBar_level.setMax(expNextLvl);
        progressBar_level.setProgress(expCurrent);
        textViewCurrentExp.setText(ManagerBrain.goFormatString(expCurrent));
        textViewNextLvlExp.setText(ManagerBrain.goFormatString(expNextLvl));
        textViewHiScore.setText("" + score);
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getExpCurrent() {
        return expCurrent;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        updateValues();
    }

    public void setLevel(int level) {
        this.level = level;
        updateValues();
    }

    public void setExpCurrent(int expCurrent) {
        this.expCurrent = expCurrent;
        updateValues();
    }

    public void setExpNextLvl(int expNextLvl) {
        this.expNextLvl = expNextLvl;
        updateValues();
    }

    public void setScore(int score) {
        this.score = score;
        updateValues();
    }
}
