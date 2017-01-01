package com.example.windzlord.brainfuck.objects;

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
import com.example.windzlord.brainfuck.managers.Gogo;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WindzLord on 12/27/2016.
 */

public class GameLayout extends FrameLayout {

    @BindView(R.id.imageView_game)
    ImageView imageViewGame;

    @BindView(R.id.textView_game)
    TextView textViewGame;

    @BindView(R.id.textView_level)
    TextView textViewLevexl;

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

    public GameLayout(Context context) {
        super(context);
        initFromContext(context, null);
    }

    public GameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFromContext(context, attrs);
    }

    public GameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromContext(context, attrs);
    }

    private void initFromContext(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.x_custom_game_layout, this);
        ButterKnife.bind(this, view);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GameLayout);

        String name = typedArray.getString(R.styleable.GameLayout_name);
        Drawable drawable = typedArray.getDrawable(R.styleable.GameLayout_src);
        if (drawable == null) drawable = getResources().getDrawable(R.mipmap.ic_launcher);

        boolean unlocked = typedArray.getBoolean(R.styleable.GameLayout_unlocked, false);

        int level = typedArray.getInteger(R.styleable.GameLayout_level, 1);
        int expCurrent = typedArray.getInteger(R.styleable.GameLayout_exp_current, 0);
        int expNextLvl = typedArray.getInteger(R.styleable.GameLayout_exp_nextlvl, 500);
        int hiscore = typedArray.getInteger(R.styleable.GameLayout_hiscore, 0);

        if (!unlocked) {
            level = 1;
            expCurrent = 0;
            expNextLvl = 500;
            drawable = getResources().getDrawable(R.drawable.ic_locked);
            layoutUnlocked.setVisibility(GONE);
            layoutLocked.setVisibility(VISIBLE);
        } else {
            layoutUnlocked.setVisibility(VISIBLE);
            layoutLocked.setVisibility(GONE);
        }

        imageViewGame.setImageDrawable(drawable);
        typedArray.recycle();

        textViewGame.setText(name);
        textViewLevexl.setText("Exp. Lv. " + level);
        progressBar_level.setMax(expNextLvl);
        progressBar_level.setProgress(expCurrent);
        textViewCurrentExp.setText(Gogo.goFormatString(expCurrent));
        textViewNextLvlExp.setText(Gogo.goFormatString(expNextLvl));
        textViewHiScore.setText("" + hiscore);
    }

}
