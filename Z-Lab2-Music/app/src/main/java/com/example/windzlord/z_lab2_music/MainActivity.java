package com.example.windzlord.z_lab2_music;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.windzlord.z_lab2_music.managers.Constant;
import com.example.windzlord.z_lab2_music.managers.RealmManager;
import com.example.windzlord.z_lab2_music.models.MediaType;
import com.example.windzlord.z_lab2_music.models.Song;
import com.example.windzlord.z_lab2_music.models.json_models.MediaMommy;
import com.example.windzlord.z_lab2_music.objects.event_bus.FragmentChanger;
import com.example.windzlord.z_lab2_music.objects.event_bus.MediaChanger;
import com.example.windzlord.z_lab2_music.screens.ActivityMainFragment;
import com.example.windzlord.z_lab2_music.screens.GenresFragment;
import com.example.windzlord.z_lab2_music.screens.LikedFragment;
import com.example.windzlord.z_lab2_music.screens.PlayerFragment;
import com.example.windzlord.z_lab2_music.services.MusicService;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_daddy)
    LinearLayout layoutDaddy;

    @BindView(R.id.cute_player)
    RelativeLayout cutePlayer;

    @BindView(R.id.cute_seek_bar)
    SeekBar cuteSeekBar;

    @BindView(R.id.cute_song_image)
    ImageView cuteSongImage;

    @BindView(R.id.cute_song_name)
    TextView cuteSongName;

    @BindView(R.id.cute_song_artist)
    TextView cuteSongArtist;

    @BindView(R.id.cute_image_button_go)
    ImageView cuteImageButtonGo;

    private MediaType media;
    private int indexSong;
    private boolean isPlaying;
    private CountDownTimer countDownTimer;
    private long remainTime;
    private long totalzTime = 5000;
    private RotateAnimation rotateAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingThingsUp();
    }

    private void settingThingsUp() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        getContent();
    }

    private void getContent() {
        openFragment(this.getClass().getSimpleName(), new ActivityMainFragment(), false);

        cuteSeekBar.setMax((int) totalzTime);
        cutePlayer.setVisibility(View.GONE);

        cuteSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                countDownTimerCancel();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                remainTime = totalzTime - seekBar.getProgress();
                countDownTimer = new CountDownTimer(remainTime + 1, 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        remainTime = millisUntilFinished;
                        cuteSeekBar.setProgress((int) (totalzTime - remainTime));
                    }

                    @Override
                    public void onFinish() {
                        nextSong();
                    }
                }.start();
            }
        });

        getAnimation();
    }

    private void getAnimation() {
        rotateAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
    }

    @Subscribe
    public void onFragmentEvent(FragmentChanger event) {
        openFragment(event.getClassName(), event.getFragment(), event.isAddToBackStack());
    }

    private void openFragment(String className, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (className.equals(GenresFragment.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                    R.anim.go_right_in, R.anim.go_right_out);
        }
        if (className.equals(LikedFragment.class.getSimpleName())) {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.fade_out);
        }

        fragmentTransaction.replace(R.id.layout_main, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Subscribe
    public void onMediaEvent(MediaChanger event) {
        if (!this.getClass().getSimpleName().equals(event.getClassName())) return;
        media = RealmManager.getInstance()
                .getMediaList().get(event.getIndexMedia());
        if (RealmManager.getInstance()
                .getTopSong(RealmManager.getInstance()
                        .getMediaList().get(media.getIndex()).getId())
                .isEmpty()) return;
        playMusic(event.getIndexSong());
    }

    private void playMusic(int position) {

        Song song = RealmManager.getInstance()
                .getTopSong(RealmManager.getInstance()
                        .getMediaList().get(media.getIndex()).getId())
                .get(position);

        Retrofit zingRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.GET_MP3_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MusicService musicService = zingRetrofit.create(MusicService.class);

        String search = String.format("{\"q\":\"%s %s\"," +
                        " \"sort\":\"hot\", \"start\":\"0\", \"length\":\"10\"}",
                song.getName(), song.getArtist());
        musicService.getMediaMommy(search)
                .enqueue(new Callback<MediaMommy>() {
                    @Override
                    public void onResponse(Call<MediaMommy> call, Response<MediaMommy> response) {
                        System.out.println("Zing onResponse");

                        System.out.println(call.request().url().url().toString()
                                .replaceAll("%22", "\"")
                                .replaceAll("%20", " "));
                        MediaMommy.SongZing mp3song = response.body().getFirstSong();
                        if (mp3song == null) {
                            System.out.println("NOT FOUND");
                            return;
                        }
                        System.out.println(mp3song.getLink());
                        System.out.println(mp3song.getName());
                        System.out.println(mp3song.getArtist());
                        System.out.println(mp3song.getDuration());

                        playSong(mp3song.getLink());

                        cutePlayer.setVisibility(View.VISIBLE);

                        isPlaying = true;
                        indexSong = position;
                        cuteImageButtonGo.setImageResource(R.drawable.ic_pause_white_48px);
                        totalzTime = mp3song.getDuration() * 1000;
                        cuteSeekBar.setMax((int) totalzTime);

                        cuteSongName.setText(song.getName());
                        cuteSongArtist.setText(song.getArtist());
                        ImageLoader.getInstance().displayImage(song.getImageSmall(), cuteSongImage);

                        cuteSongImage.startAnimation(rotateAnimation);
                        countDownTimerCancel();
                        countDownTimer = new CountDownTimer(totalzTime + 1, 1) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                remainTime = millisUntilFinished;
                                cuteSeekBar.setProgress((int) (totalzTime - remainTime));
                            }

                            @Override
                            public void onFinish() {
                                nextSong();
                            }
                        }.start();
                    }

                    @Override
                    public void onFailure(Call<MediaMommy> call, Throwable t) {
                        System.out.println("Zing onFailure");
                    }
                });
    }

    private ExoPlayer exoPlayer;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;

    private void playSong(String url) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Factory.newInstance(1);
        }
// String with the url of the radio you want to play
        Uri radioUri = Uri.parse(url);
// Settings for exoPlayer
        Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
        String userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        DataSource dataSource = new DefaultUriDataSource(this, null, userAgent);
        ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                radioUri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT);
        MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
// Prepare ExoPlayer
        exoPlayer.prepare(audioRenderer);
        exoPlayer.setPlayWhenReady(true);
    }

    @OnClick(R.id.cute_button_go)
    public void pauseMusic() {
        if (isPlaying) {
            isPlaying = false;
            cuteImageButtonGo.setImageResource(R.drawable.ic_play_arrow_white_48px);
            cuteSongImage.clearAnimation();
            countDownTimerCancel();
        } else {
            isPlaying = true;
            cuteImageButtonGo.setImageResource(R.drawable.ic_pause_white_48px);

            cuteSongImage.startAnimation(rotateAnimation);
            countDownTimerCancel();
            countDownTimer = new CountDownTimer(remainTime + 1, 1) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainTime = millisUntilFinished;
                    cuteSeekBar.setProgress((int) (totalzTime - remainTime));
                }

                @Override
                public void onFinish() {
                    nextSong();
                }
            }.start();
        }
    }

    private void nextSong() {
        isPlaying = false;
        cuteSeekBar.setProgress(0);
        playMusic((++indexSong) % 50);
    }

    private void countDownTimerCancel() {
        if (countDownTimer != null) countDownTimer.cancel();
    }

    @OnClick(R.id.cute_layout)
    public void showPlayer() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.go_up, R.anim.nothing,
                        R.anim.nothing, R.anim.go_down)
                .replace(R.id.layout_mommy, new PlayerFragment())
                .addToBackStack(null).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public LinearLayout getLayoutDaddy() {
        return layoutDaddy;
    }

    public MediaType getMedia() {
        return media;
    }

    public void setMedia(MediaType media) {
        this.media = media;
    }

    public int getIndexSong() {
        return indexSong;
    }

    public void setIndexSong(int indexSong) {
        this.indexSong = indexSong;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    public void setCountDownTimer(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
    }

    public long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(long remainTime) {
        this.remainTime = remainTime;
    }

    public long getTotalzTime() {
        return totalzTime;
    }

    public void setTotalzTime(long totalzTime) {
        this.totalzTime = totalzTime;
    }

    public SeekBar getCuteSeekBar() {
        return cuteSeekBar;
    }

    public void setCuteSeekBar(SeekBar cuteSeekBar) {
        this.cuteSeekBar = cuteSeekBar;
    }

    public RotateAnimation getRotateAnimation() {
        return rotateAnimation;
    }
}
