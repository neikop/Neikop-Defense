package com.example.windzlord.brainfuck.managers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.windzlord.brainfuck.objects.MessageManager;
import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import org.greenrobot.eventbus.EventBus;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by WindyKiss on 1/4/2017.
 */

public class ManagerServer {

    private final String TAG = this.getClass().getSimpleName();

    private MobileServiceTable<HighScore> mServiceTable;

    private ManagerServer(Context context) {
        try {
            MobileServiceClient mClient = new MobileServiceClient(
                    "https://brainluck.azurewebsites.net", context);

            mClient.setAndroidHttpClientFactory(() -> {
                OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(5, TimeUnit.SECONDS);
                client.setWriteTimeout(5, TimeUnit.SECONDS);
                return client;
            });
            mServiceTable = mClient.getTable(HighScore.class);
        } catch (MalformedURLException ignored) {
        }
    }

    private static ManagerServer instance;

    public static ManagerServer getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerServer(context);
    }

    public void uploadLocalToServer(String userID) {
        if (!ManagerNetwork.getInstance().isConnectedToInternet()) return;
        if (userID.isEmpty()) downloadServerToLocal();
        else {
            Log.d(TAG, "uploadLocalToServer");
            ManagerUserData.getInstance().updateDatabaseFromPreference();
            uploadScores(ManagerUserData.getInstance().getScoreByUserId(userID));
        }
    }

    public void uploadScores(List<HighScore> scores) {
        if (scores.isEmpty()) return;
        Log.d(TAG, "Begin upload " + scores.get(0).getUserName());
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (HighScore score : scores) {
                    try {
                        mServiceTable.update(score).get();
                        Log.d(TAG, "Done uploadScore: " + score);
                    } catch (ExecutionException | InterruptedException ignored) {
                        Log.d(TAG, "Fail uploadScore: " + score);
                    }
                }
                downloadServerToLocal();
                return null;
            }
        });
    }

    private void downloadServerToLocal() {
        Log.d(TAG, "downloadServerToLocal");
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    List<HighScore> scores = getListScoreServer();
                    if (!ManagerBrain.GAME_LOOPER_SYNC)
                        EventBus.getDefault().post(new MessageManager("", "Server good"));

                    Log.d(TAG, "Scores on server: " + scores.size());
                    Log.d(TAG, "Scores on local before: "
                            + ManagerUserData.getInstance().getListScore().size());
                    ManagerUserData.getInstance().updateDatabase(scores);
                    Log.d(TAG, "Scores on local after: "
                            + ManagerUserData.getInstance().getListScore().size());
                } catch (ExecutionException | InterruptedException | MobileServiceException serverException) {
                    if (!ManagerBrain.GAME_LOOPER_SYNC)
                        EventBus.getDefault().post(new MessageManager("Warning", "Server down"));
                    Log.d(TAG, "Server Down! " + serverException.toString());
                }
                return null;
            }
        });
    }

    private List<HighScore> getListScoreServer()
            throws ExecutionException, InterruptedException, MobileServiceException {
        return mServiceTable.execute().get();
    }

    // When END GAME
    public void uploadSingleScore(HighScore score) {
        Log.d(TAG, "END GAME - Begin upload " + score);
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mServiceTable.update(score).get();
                    Log.d(TAG, "END GAME - Done uploadScore: " + score);
                } catch (ExecutionException | InterruptedException ignored) {
                    Log.d(TAG, "END GAME - Fail uploadScore: " + score);
                }
                return null;
            }
        });
    }

    // When Login
    public void checkExistedUser(String userID) {
        Log.d(TAG, "checkExistedUser " + userID);
        if (ManagerUserData.getInstance().isExistedUser(userID))
            updatePreferenceWhenLogin(userID);
        else createNewUserWhenLogin(userID);
    }

    // When Login
    private void updatePreferenceWhenLogin(String userID) {
        Log.d(TAG, "updatePreferenceWhenLogin " + userID);
        List<HighScore> scores = ManagerUserData.getInstance().getScoreByUserId(userID);
        for (HighScore score : scores) {
            ManagerPreference.getInstance().putLevel(score.getType(), score.getPosition(), score.getLevel());
            ManagerPreference.getInstance().putExpCurrent(score.getType(), score.getPosition(), score.getExp());
            ManagerPreference.getInstance().putScore(score.getType(), score.getPosition(), score.getScore());
        }
    }

    // When Login
    private void createNewUserWhenLogin(String userID) {
        Log.d(TAG, "createNewUserWhenLogin");
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i < ManagerBrain.GAME_LIST.length; i++) {
                    for (int k = 1; k < 4; k++) {
                        HighScore score = new HighScore();
                        score.setUserId(userID);
                        score.setType(ManagerBrain.GAME_LIST[i]);
                        score.setUserName(ManagerPreference.getInstance().getUserName());
                        score.setUserImage(ManagerPreference.getInstance().getUserImage());
                        score.setPosition(k);
                        score.setLevel(ManagerPreference.getInstance().getLevel(ManagerBrain.GAME_LIST[i], k));
                        score.setExp(ManagerPreference.getInstance().getExpCurrent(ManagerBrain.GAME_LIST[i], k));
                        score.setScore(ManagerPreference.getInstance().getScore(ManagerBrain.GAME_LIST[i], k));
                        try {
                            mServiceTable.insert(score).get();
                            Log.d(TAG, "insertScore to SERVER " + score.getUserName() + ManagerBrain.GAME_LIST[i] + k);
                        } catch (InterruptedException | ExecutionException ignored) {
                        }
                    }
                }
                downloadServerToLocal();
                updatePreferenceWhenLogin(userID);
                return null;
            }
        });
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

}