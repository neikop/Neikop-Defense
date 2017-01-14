package com.example.windzlord.brainfuck.screens.tabs;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.windzlord.brainfuck.MainActivity;
import com.example.windzlord.brainfuck.R;
import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.managers.ManagerFile;
import com.example.windzlord.brainfuck.managers.ManagerPreference;
import com.example.windzlord.brainfuck.managers.ManagerServer;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSetting extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.button_setting_facebook)
    LoginButton buttonFacebook;

    @BindView(R.id.button_setting_back)
    Button buttonBack;

    @BindView(R.id.button_sound)
    Button buttonSound;

    @BindView(R.id.imageView_sound)
    ImageView imageViewSound;

    @BindView(R.id.button_music)
    Button buttonMusic;

    @BindView(R.id.imageView_music)
    ImageView imageViewMusic;


    public FragmentSetting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_fragment_setting, container, false);
        settingThingsUp(view);

        return view;
    }

    private void settingThingsUp(View view) {
        ButterKnife.bind(this, view);

        getInfo();
        settingFacebook();
    }

    private void getInfo() {
        buttonSound.setText(ManagerPreference.getInstance().getSound() ?
                "STOP SOUND" : "PLAY SOUND");
        imageViewSound.setImageResource(ManagerPreference.getInstance().getSound() ?
                R.drawable.icon_sound_on : R.drawable.icon_sound_off);
        buttonMusic.setText(ManagerPreference.getInstance().getMusic() ?
                "STOP MUSIC" : "PLAY MUSIC");
        imageViewMusic.setImageResource(ManagerPreference.getInstance().getMusic() ?
                R.drawable.icon_music_on : R.drawable.icon_music_off);
    }

    @OnClick(R.id.button_setting_back)
    public void onBackPressed() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.button_sound)
    public void onButtonSoundPressed() {
        boolean active = ManagerPreference.getInstance().getSound();
        if (active) {
            imageViewSound.setImageResource(R.drawable.icon_sound_off);
            buttonSound.setText("PLAY SOUND");
        } else {
            imageViewSound.setImageResource(R.drawable.icon_sound_on);
            buttonSound.setText("STOP SOUND");
        }
        ManagerPreference.getInstance().putSound(!active);
    }

    @OnClick(R.id.button_music)
    public void onButtonMusicPressed() {
        boolean active = ManagerPreference.getInstance().getMusic();
        if (active) {
            imageViewMusic.setImageResource(R.drawable.icon_music_off);
            buttonMusic.setText("PLAY MUSIC");
        } else {
            imageViewMusic.setImageResource(R.drawable.icon_music_on);
            buttonMusic.setText("STOP MUSIC");
        }
        ManagerPreference.getInstance().putMusic(!active);
    }

    public void settingFacebook() {
        AppEventsLogger.activateApp(getContext());
        buttonFacebook.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        buttonFacebook.registerCallback(
                ((MainActivity) getActivity()).getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "onSuccess");
                        new ProfileTracker() {
                            @Override
                            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                if (currentProfile != null)
                                    login(currentProfile);
                                else logout();
                            }
                        }.startTracking();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "onError");
                    }
                });
    }

    private void login(Profile profile) {
        Log.d(TAG, "LOGIN");
        String userID = profile.getId(), userName = profile.getName();
        ManagerPreference.getInstance().putUserID(userID);
        ManagerPreference.getInstance().putUserName("N'" + userName + "'");

        ManagerServer.getInstance().checkExistedUser(userID);
        //load Image
        String url = profile.getProfilePictureUri(300, 300).toString();
        new FragmentSetting.DownloadImage().execute(url);
    }

    private void logout() {
        Log.d(TAG, "LOGOUT");
        ManagerServer.getInstance().uploadLocalToServer(
                ManagerPreference.getInstance().getUserID());
        new CountDownTimerAdapter(500) {
            @Override
            public void onFinish() {
                ManagerPreference.getInstance().putUserID("");
                ManagerPreference.getInstance().putUserName("N'Guest'");
            }
        }.start();
    }

    private class DownloadImage extends AsyncTask<Object, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Object... object) {
            String sURL = (String) object[0];
            try {
                InputStream in = (InputStream) new URL(sURL).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();
                return bitmap;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ManagerFile.getInstance().createImage(result, ManagerPreference.getInstance().getUserID());
        }
    }
}
