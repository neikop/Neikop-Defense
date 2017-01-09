package com.example.windzlord.brainfuck.managers;

/**
 * Created by WindyKiss on 1/4/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ManagerServer {

    private static final String TAG = ManagerServer.class.getSimpleName();

    private MobileServiceTable<HighScore> mServiceTable;
    private List<HighScore> listHighScore;

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
            listHighScore = new ArrayList<>();
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

    public void createNewUser(String userId) {
        listHighScore = SQLiteDBHelper.getInstance().getAllHighscoreByUserId(userId);


        if (listHighScore.isEmpty()) {
            for (int i = 0; i < Gogo.GAME_LIST.length; i++) {
                for (int k = 1; k < 4; k++) {
                    HighScore score = new HighScore();
                    score.setUserId(userId);
                    score.setType(Gogo.GAME_LIST[i]);
                    score.setPosition(k);
                    score.setLevel(1);
                    Log.d(TAG, score.toString());
                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {

                                mServiceTable.insert(score).get();

                            } catch (Exception ignored) {
                            }
                            return null;
                        }
                    };
                    runAsyncTask(task);
                }
            }

            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        List<HighScore> result = getAllHighscore();

                        Log.d(TAG, result.size() + "");

                        SQLiteDBHelper.getInstance().resetDB(result);
                        updateData(userId);
                        Log.d(TAG, SQLiteDBHelper.getInstance().getAllHighscore().size() + "");
                    } catch (ExecutionException | InterruptedException | MobileServiceException ignored) {
                    }
                    return null;
                }
            };
            runAsyncTask(task);
        } else {
            updateData(userId);
        }
    }

    private void updateData(String userID) {

        listHighScore = SQLiteDBHelper.getInstance().getAllHighscoreByUserId(userID);


        for (HighScore tmpHighScore : listHighScore) {
            Log.d(TAG, tmpHighScore.getLevel() + "---" + tmpHighScore.getExpCurrent() + "---" + tmpHighScore.getHighscore());
            ManagerPreference.getInstance().putLevel(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getLevel());
            ManagerPreference.getInstance().putExpCurrent(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getExpCurrent());
            ManagerPreference.getInstance().putScore(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getHighscore());
        }

    }


    private List<HighScore> getAllHighscore() throws ExecutionException, InterruptedException, MobileServiceException {
        return mServiceTable.execute().get();
    }

    //When App Start got 1 user logged already
    public void settingStartApp(String userID) {
        //upload local to Server
        List<HighScore> results = SQLiteDBHelper.getInstance().getAllHighscoreByUserId(userID);
        if(!results.isEmpty()){
            for (HighScore score : results) {
                updateSingleHighscore(score);
            }
        }

        Log.d(TAG, "Begin sync");
        syncDown();

    }

    public void updateSingleHighscore(HighScore score){
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mServiceTable.update(score).get();
                    Log.d(TAG, "updated");
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        };
        runAsyncTask(task);
    }

    //Sync From Server to SQLite local
    private void syncDown() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    List<HighScore> result = getAllHighscore();

                    Log.d(TAG, result.size() + "");

                    SQLiteDBHelper.getInstance().resetDB(result);

                    Log.d(TAG, SQLiteDBHelper.getInstance().getAllHighscore().size() + "");
                } catch (ExecutionException | InterruptedException | MobileServiceException ignored) {
                }
                return null;
            }
        };
        runAsyncTask(task);
    }


    //When User Login


    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

}
