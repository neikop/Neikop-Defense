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
    private final int TIME = 5000;

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

    private List<HighScore> getHighScoreByUserID(String userID)
            throws ExecutionException, InterruptedException {
        return mServiceTable.where().field("userId").eq(userID).execute().get();
    }

    public void gameStart(String userId) {
        if (ManagerNetwork.getInstance().isConnectedToInternet())
            if (!userId.isEmpty())
                uploadData(userId);
    }

    public void gameLogin(String userID) {

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

        new CountDownTimerAdapter(TIME, 1) {
            public void onFinish() {
                if (listHighScore.isEmpty()) {
                    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                for (int i = 0; i < Gogo.GAME_LIST.length; i++) {
                                    for (int k = 1; k < 4; k++) {
                                        HighScore score = new HighScore();
                                        score.setUserId(userID);
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
                    new CountDownTimerAdapter(TIME, 1) {
                        public void onFinish() {
                            updateData(userID);
                            ManagerPreference.getInstance().putUserID(userID);

                        }
                    }.start();
                } else {
                    updateData(userID);
                    ManagerPreference.getInstance().putUserID(userID);
                }
            }
        }.start();
    }

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

        new CountDownTimerAdapter(TIME, 1) {
            public void onFinish() {
                for (HighScore score : listHighScore) {
                    Log.d(TAG, score.getLevel() + "---" + score.getExpCurrent() + "---" + score.getHighscore());
                    ManagerPreference.getInstance().putLevel(score.getType(), score.getPosition(), score.getLevel());
                    ManagerPreference.getInstance().putExpCurrent(score.getType(), score.getPosition(), score.getExpCurrent());
                    ManagerPreference.getInstance().putScore(score.getType(), score.getPosition(), score.getHighscore());
                }
            }
        }.start();
    }

    private void uploadData(String userID) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    listHighScore = getHighScoreByUserID(userID);
                    for (String type : Gogo.GAME_LIST) {
                        for (int position = 1; position < 4; position++) {
                            try {
                                uploadHighScore(type, position,
                                        ManagerPreference.getInstance().getExpCurrent(type, position),
                                        ManagerPreference.getInstance().getLevel(type, position),
                                        ManagerPreference.getInstance().getScore(type, position)
                                );
                            } catch (ExecutionException | InterruptedException ignored) {
                            }
                        }
                    }
                } catch (ExecutionException | InterruptedException ignored) {
                }
                return null;
            }
        };
        runAsyncTask(task);
    }

    private void uploadHighScore(String type, int position, int nExp, int nLvl, int nHighScore) throws ExecutionException, InterruptedException {
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

    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }

}
