package com.example.windzlord.z_lab2_music.screens;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.windzlord.z_lab2_music.MainActivity;
import com.example.windzlord.z_lab2_music.R;
import com.example.windzlord.z_lab2_music.managers.Constant;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.models.Song;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    @BindView(R.id.cute_song_image)
    ImageView cuteSongImage;

    @BindView(R.id.cute_song_name)
    TextView cuteSongName;

    @BindView(R.id.cute_song_artist)
    TextView cuteSongArtist;

    @BindView(R.id.progress_seek_bar)
    SeekBar progressSeekBar;

    @BindView(R.id.text_time_passed)
    TextView textPassedTime;

    @BindView(R.id.text_time_total)
    TextView textTotalzTime;

    MainActivity activity;

    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        getContent();
        new CountDownTimer(500, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                activity.getLayoutDaddy().setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.getLayoutDaddy().setVisibility(View.VISIBLE);
    }

    private void getContent() {
        activity = (MainActivity) getActivity();
        Song song = RealmManager.getInstance()
                .getTopSong(RealmManager.getInstance()
                        .getMediaList().get(activity.getMedia().getIndex()).getId())
                .get(activity.getIndexSong());
        cuteSongName.setText(song.getName());
        cuteSongArtist.setText(song.getArtist());
        ImageLoader.getInstance().displayImage(song.getImageBigger(), cuteSongImage);

        progressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textPassedTime.setText(Constant.toTime(progress / 1000));
                textTotalzTime.setText(Constant.toTime(seekBar.getMax() / 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        progressSeekBar.setMax((int) activity.getTotalzTime());
        progressSeekBar.setProgress((int) (activity.getTotalzTime() - activity.getRemainTime()));
    }

    @OnClick(R.id.button_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

}
