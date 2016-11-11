package com.example.windzlord.x_session9;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import layout.BlankFragment;
import layout.BlankFragment2;

public class MainActivity extends AppCompatActivity {

    private View view;
    private boolean ok = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.view_item);
        if (ok) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.layoutFrame, new BlankFragment2())
                    .commit();
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        menu.findItem(R.id.menu_item_animate).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                System.out.println("onMenuItemClick");
//                if (ok)
//                    homework02();
//                else homework01();
                animationSet();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void homework02() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit)
                .replace(R.id.layoutFrame, new BlankFragment())
                .commit();
    }

    private void homework01() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 0, 2, 1, 0, 0);
        translateAnimation.setDuration(400);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new LinearInterpolator());

        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimation);

        set.setFillAfter(true);
        view.startAnimation(set);
    }

    private void translate() {
        TranslateAnimation animation = new TranslateAnimation(
                0, 100,
                0, 50
        );
        animation.setDuration(300);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        view.startAnimation(animation);
    }

    private void translateByXML() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.move_cross);
        view.startAnimation(animation);
    }

    private void rotate() {
        RotateAnimation rotateAnimation = new RotateAnimation(20, 90, 0, 0);
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);
    }

    private void scale() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 2, 0, 2);
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

    private void animationSet() {
        AnimationSet animationSet = new AnimationSet(false);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 0, 0);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1, 2);
        animationSet.setDuration(1000);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);

        final AnimationSet animationSet2 = new AnimationSet(true);
        RotateAnimation rotateAnimation2 = new RotateAnimation(0, 360, 0, 0);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(2, 1, 2, 1);
        animationSet2.setDuration(1000);
        animationSet2.setInterpolator(new LinearInterpolator());
        animationSet2.addAnimation(rotateAnimation2);
        animationSet2.addAnimation(scaleAnimation2);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(animationSet2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animationSet);
    }
}
