package com.example.windzlord.brainfuck.managers;

/**
 * Created by WindyKiss on 1/4/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.example.windzlord.brainfuck.adapters.CountDownTimerAdapter;
import com.example.windzlord.brainfuck.objects.models.HighScore;
import com.facebook.Profile;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ManagerServer {

    private final int TIME = 3000;

    private MobileServiceTable<HighScore> mServiceTable;
    private List<HighScore> scores;

    private ManagerServer(Context context) {
        try {
            MobileServiceClient mClient = new MobileServiceClient(
                    "https://brainluck.azurewebsites.net", context);

            mClient.setAndroidHttpClientFactory(() -> {
                OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(3, TimeUnit.SECONDS);
                client.setWriteTimeout(3, TimeUnit.SECONDS);
                return client;
            });
            mServiceTable = mClient.getTable(HighScore.class);
            scores = new ArrayList<>();
        } catch (Exception goAgain) {
            init(context);
        }
    }

    private static ManagerServer instance;

    public static ManagerServer getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new ManagerServer(context);
    }

    private List<HighScore> getHighScoreByUserID(String userID) {
        try {
            return mServiceTable.where().field("userId").eq(userID).execute().get();
        } catch (InterruptedException | ExecutionException ignored) {
            return null;
        }
    }

    public void gameUpload(String userID) {
        if (ManagerNetwork.getInstance().isConnectedToInternet())
            if (!userID.isEmpty())
                uploadData(userID);
    }

    private void uploadData(String userID) {
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                scores = getHighScoreByUserID(userID);
                for (String type : Gogo.GAME_LIST)
                    for (int position = 1; position < 4; position++) {
                        int exp = ManagerPreference.getInstance().getExpCurrent(type, position);
                        int level = ManagerPreference.getInstance().getLevel(type, position);
                        int score = ManagerPreference.getInstance().getScore(type, position);
                        uploadScore(type, position, exp, level, score);
                    }
                return null;
            }
        });
    }

    private void uploadScore(String type, int position, int nExp, int nLvl, int nHighScore) {
        for (HighScore score : scores) {
            if (score.getType().equals(type) & score.getPosition() == position) {
                score.setExpCurrent(nExp);
                score.setScore(nHighScore);
                score.setLevel(nLvl);
                System.out.println(score.getLevel() + "---" + score.getExpCurrent() + "---" + score.getScore());
                runAsyncTask(new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            mServiceTable.update(score).get();
                        } catch (ExecutionException | InterruptedException ignored) {
                        }
                        return null;
                    }
                });
                break;
            }
        }
    }

    public void gameLogin(Profile profile) {
        String userID = profile.getId();
        String userName = profile.getName();
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                scores = getHighScoreByUserID(userID);
                return null;
            }
        });
        new CountDownTimerAdapter(TIME, 1) {
            public void onFinish() {
                if (scores.isEmpty()) {
                    runAsyncTask(new AsyncTask<Void, Void, Void>() {
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
                                        mServiceTable.insert(score).get();
                                    }
                                }
                            } catch (InterruptedException | ExecutionException ignored) {
                            }
                            return null;
                        }
                    });
                    new CountDownTimerAdapter(TIME, 1) {
                        public void onFinish() {
                            updateData(userID);
                        }
                    }.start();
                } else {
                    updateData(userID);
                }
            }
        }.start();
    }

    private void updateData(String userID) {
        runAsyncTask(new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                scores = getHighScoreByUserID(userID);
                return null;
            }
        });
        new CountDownTimerAdapter(TIME, 1) {
            public void onFinish() {
                for (HighScore score : scores) {
                    System.out.println(score.getLevel() + "---" + score.getExpCurrent() + "---" + score.getScore());
                    ManagerPreference.getInstance().putLevel(score.getType(), score.getPosition(),
                            score.getLevel());
                    ManagerPreference.getInstance().putExpCurrent(score.getType(), score.getPosition(),
                            score.getExpCurrent());
                    ManagerPreference.getInstance().putScore(score.getType(), score.getPosition(),
                            score.getScore());
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
