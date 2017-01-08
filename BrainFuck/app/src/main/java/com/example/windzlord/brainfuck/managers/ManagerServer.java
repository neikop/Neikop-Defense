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
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    listHighScore = getHighScoreByUserID(userId);
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        };
        runAsyncTask(task);

        new CountDownTimerAdapter(5000, 1) {
            public void onFinish() {
                if (listHighScore.isEmpty()) {
                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                for (int i = 0; i < Gogo.GAME_LIST.length; i++) {
                                    for (int k = 1; k < 4; k++) {
                                        HighScore score = new HighScore();
                                        score.setUserId(userId);
                                        score.setType(Gogo.GAME_LIST[i]);
                                        score.setPosition(k);
                                        score.setLevel(1);
                                        Log.d(TAG, score.toString());
                                        mServiceTable.insert(score).get();
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            return null;
                        }
                    };
                    runAsyncTask(task);
                    new CountDownTimerAdapter(5000, 1) {
                        public void onFinish() {
                            updateData(userId);
                        }
                    }.start();
                } else {
                    updateData(userId);
                }
            }
        }.start();
    }

    private List<HighScore> getHighScoreByUserID(String userID) throws ExecutionException, InterruptedException {
        return mServiceTable.where().field("userId").eq(userID).execute().get();
    }

    //When App Start got 1 user logged already
    public void settingStartApp(String userID) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    listHighScore = getHighScoreByUserID(userID);
                    uploadData();
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        };
        runAsyncTask(task);
    }

    private void uploadData() {
        for (int i = 0; i < Gogo.GAME_LIST.length; i++) {
            for (int j = 1; j < 4; j++) {
                try {
                    updateHighScore(Gogo.GAME_LIST[i], j,
                            ManagerPreference.getInstance().getExpCurrent(Gogo.GAME_LIST[i], j),
                            ManagerPreference.getInstance().getLevel(Gogo.GAME_LIST[i], j),
                            ManagerPreference.getInstance().getScore(Gogo.GAME_LIST[i], j)
                    );
                } catch (ExecutionException | InterruptedException ignored) {
                }
            }
        }
    }

    private void updateHighScore(String type, int position, int nExp, int nLvl, int nHighScore) throws ExecutionException, InterruptedException {
        for (HighScore score : listHighScore) {
            if (score.getType().equals(type) && score.getPosition() == position) {
                score.setExpCurrent(nExp);
                score.setHighscore(nHighScore);
                score.setLevel(nLvl);
                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            mServiceTable.update(score).get();
                        } catch (ExecutionException | InterruptedException ignored) {
                        }
                        return null;
                    }
                };
                runAsyncTask(task);
                break;
            }
        }
    }

    //When User Login
    private void updateData(String userID) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    listHighScore = getHighScoreByUserID(userID);
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        };
        runAsyncTask(task);

        new CountDownTimerAdapter(5000, 1) {
            public void onFinish() {
                for (HighScore tmpHighScore : listHighScore) {
                    Log.d(TAG, tmpHighScore.getLevel() + "---" + tmpHighScore.getExpCurrent() + "---" + tmpHighScore.getHighscore());
                    ManagerPreference.getInstance().putLevel(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getLevel());
                    ManagerPreference.getInstance().putExpCurrent(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getExpCurrent());
                    ManagerPreference.getInstance().putScore(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getHighscore());
                }
            }
        }.start();
    }

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

}
