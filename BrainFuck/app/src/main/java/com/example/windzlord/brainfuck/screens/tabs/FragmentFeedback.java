package com.example.windzlord.brainfuck.screens.tabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windzlord.brainfuck.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.example.windzlord.brainfuck.R;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFeedback extends Fragment {

    private static final String TAG = FragmentFeedback.class.getSimpleName();
    private CallbackManager callbackManager;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.tv_ad)
    TextView ad;

    public FragmentFeedback() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getContext());
        View v = inflater.inflate(R.layout.tab_fragment_feedback, container, false);
        ButterKnife.bind(this, v);
        loginfacebook();
        return v;
    }

    public void loginfacebook() {
        AppEventsLogger.activateApp(getContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        Log.d(TAG,"abc");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"onSuccess");
                AccessToken accessToken = loginResult.getAccessToken();
                AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                    }
                };
                accessTokenTracker.startTracking();

                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        if (currentProfile != null) {
                            ad.setText(currentProfile.getFirstName() + " " + currentProfile.getLastName() + "" + currentProfile.getId());
//                            String url = currentProfile.getProfilePictureUri(700, 700).toString();
                        }
                        Toast.makeText(getActivity(), "Successful", Toast.LENGTH_LONG).show();
                    }
                };
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"onError");

            }
        });

    }



}
