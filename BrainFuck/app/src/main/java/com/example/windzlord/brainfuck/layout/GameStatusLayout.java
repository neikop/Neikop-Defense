package com.example.windzlord.brainfuck.layout;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.windzlord.brainfuck.R;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameStatusLayout extends FrameLayout {

    @BindView(R.id.progressBar_time)
    ProgressBar progressBarTime;

    @BindView(R.id.progressBar_going)
    ProgressBar progressBarGoing;

    @BindView(R.id.textView_time)
    TextView textViewTime;

    @BindView(R.id.textView_going)
    TextView textViewGoing;

    @BindView(R.id.button_pause)
    CircleButton buttonPause;

    private int timeCount;
    private int timeMax;
    private int goingProgress;
    private int goingCount;
    private int goingMax;
    private int timeProgress;


    public GameStatusLayout(Context context) {
        super(context);
        initFromContext(context, null);
    }

    public GameStatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFromContext(context, attrs);
    }

    public GameStatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFromContext(context, attrs);
    }

    private void initFromContext(Context context, AttributeSet attrs) {
        View view = inflate(context, R.layout.custom_game_status_layout, this);
        ButterKnife.bind(this, view);

        getValues(context.obtainStyledAttributes(attrs, R.styleable.GameStatusLayout));

    }

    private void getValues(TypedArray typedArray) {
        timeCount = typedArray.getInteger(R.styleable.GameStatusLayout_time_count, 100);
        timeMax = typedArray.getInteger(R.styleable.GameStatusLayout_time_max, 5000);
        timeProgress = typedArray.getInteger(R.styleable.GameStatusLayout_time_progress, 5000);
        goingCount = typedArray.getInteger(R.styleable.GameStatusLayout_going_count, 0);
        goingMax = typedArray.getInteger(R.styleable.GameStatusLayout_going_max, 10);
        goingProgress = typedArray.getInteger(R.styleable.GameStatusLayout_going_progress, 0);
        typedArray.recycle();
        updateValues();
    }

    private void updateValues() {
        textViewTime.setText("" + timeCount);
        progressBarTime.setMax(timeMax);
        progressBarTime.setProgress(timeProgress);
        textViewGoing.setText("" + goingCount);
        progressBarGoing.setMax(goingMax);
        progressBarGoing.setProgress(goingProgress);
    }

    public void updateValues(int timeCount, int timeMax, int timeProgress, int goingCount, int goingMax, int goingProgress) {
        this.timeCount = timeCount;
        this.timeMax = timeMax;
        this.timeProgress = timeProgress;
        this.goingCount = goingCount;
        this.goingProgress = goingProgress;
        this.goingMax = goingMax;
        updateValues();
    }

    public void setPauseListener(OnClickListener listener) {
        buttonPause.setOnClickListener(listener);
    }

    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
        updateValues();
    }

    public int getTimeCount() {
        return Integer.parseInt("" + textViewTime.getText());
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
        updateValues();
    }

    public void setTimeProgress(int timeProgress) {
        this.timeProgress = timeProgress;
        updateValues();
    }

    public void setGoingCount(int goingCount) {
        this.goingCount = goingCount;
        updateValues();
    }

    public void setGoingProgress(int goingProgress) {
        this.goingProgress = goingProgress;
        updateValues();
    }

    public void setGoingMax(int goingMax) {
        this.goingMax = goingMax;
        updateValues();
    }
}
