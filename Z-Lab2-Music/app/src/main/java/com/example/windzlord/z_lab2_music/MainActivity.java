package com.example.windzlord.z_lab2_music;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.windzlord.z_lab2_music.services.MediaDownloader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar myToolbar;

    @BindView(R.id.tab_layout)
    TabLayout myTabLayout;

    @BindView(R.id.pager)
    ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        myToolbar.setTitle("Explore");
        setSupportActionBar(myToolbar);

        myTabLayout.addTab(myTabLayout.newTab().setText("GENRES"));
        myTabLayout.addTab(myTabLayout.newTab().setText("PLAYLIST"));
        myTabLayout.addTab(myTabLayout.newTab().setText("OFFLINE"));
        myTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter(
                getSupportFragmentManager(), myTabLayout.getTabCount());
        myViewPager.setAdapter(adapter);
        myViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(myTabLayout));
        myTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            goSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goSearch() {

    }

}
