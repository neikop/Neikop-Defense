package com.example.windzlord.brainfuck.managers;

/**
 * Created by WindyKiss on 1/4/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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

    // When Start Game and Logout
    public void uploadLocalToServer(String userID) {
        if (ManagerUserData.getInstance().isExistedUser(userID)) {
            System.out.println("uploadLocalToServer");
            List<HighScore> scores = ManagerUserData.getInstance().getScoreByUserId(userID);
            uploadScores(scores);
        }
        downloadServerToLocal();
    }

    public void uploadScores(List<HighScore> scores) {
        System.out.println("uploadScores");
        if (scores.isEmpty()) return;
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    for (HighScore score : scores) {
                        mServiceTable.update(score).get();
                    }
                    Log.d(TAG, "Update Done");
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        });
    }


    // When End Game
    public void uploadSingleScore(HighScore score) {
        System.out.println("uploadScores");
        if (score == null) return;
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mServiceTable.update(score).get();
                    Log.d(TAG, "Update Done");
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        });
    }

    // After Upload
    private void downloadServerToLocal() {
        System.out.println("downloadServerToLocal");
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    List<HighScore> scores = getListScore();
                    Log.d(TAG, scores.size() + "");
                    ManagerUserData.getInstance().resetDatabase(scores);
                    Log.d(TAG, ManagerUserData.getInstance().getListScore().size() + "");
                } catch (ExecutionException | InterruptedException | MobileServiceException ignored) {
                }
                return null;
            }
        });
    }

    private List<HighScore> getListScore() throws ExecutionException, InterruptedException, MobileServiceException {
        return mServiceTable.execute().get();
    }

    // When Login
    public void checkExistedUser(String userId) {
        System.out.println("checkExistedUser");
        if (ManagerUserData.getInstance().isExistedUser(userId))
            updateData(userId);
        else createNewUser(userId);
    }

    private void updateData(String userID) {
        System.out.println("updateData");
        List<HighScore> scores = ManagerUserData.getInstance().getScoreByUserId(userID);
        for (HighScore score : scores) {
            ManagerPreference.getInstance().putLevel(score.getType(), score.getPosition(), score.getLevel());
            ManagerPreference.getInstance().putExpCurrent(score.getType(), score.getPosition(), score.getExpCurrent());
            ManagerPreference.getInstance().putScore(score.getType(), score.getPosition(), score.getScore());
        }
    }

    private void createNewUser(String userId) {
        System.out.println("createNewUser");
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    for (int i = 0; i < Gogo.GAME_LIST.length; i++) {
                        for (int k = 1; k < 4; k++) {
                            HighScore score = new HighScore();
                            score.setUserId(userId);
                            score.setType(Gogo.GAME_LIST[i]);
                            score.setPosition(k);
                            score.setLevel(ManagerPreference.getInstance().getLevel(Gogo.GAME_LIST[i], k));
                            score.setExpCurrent(ManagerPreference.getInstance().getLevel(Gogo.GAME_LIST[i], k));
                            score.setScore(ManagerPreference.getInstance().getLevel(Gogo.GAME_LIST[i], k));
                            mServiceTable.insert(score).get();
                            Log.d(TAG, "Inserted");
                        }
                    }
                    Log.d(TAG, "Insert Done");
                    List<HighScore> scores = getListScore();
                    Log.d(TAG, scores.size() + "");

                    ManagerUserData.getInstance().resetDatabase(scores);
                    updateData(userId);
                    Log.d(TAG, ManagerUserData.getInstance().getListScore().size() + "");
                } catch (Exception ignored) {
                }
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