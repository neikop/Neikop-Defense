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
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DBContextSV {
    private static final String TAG = DBContextSV.class.getSimpleName();
    private MobileServiceTable<HighScore> mServiceTable;
    private List<HighScore>tmpListHighScore;

    public DBContextSV(Context context) {
        try {
            MobileServiceClient mClient = new MobileServiceClient(
                    "https://brainluck.azurewebsites.net",
                    context
            );

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });

            this.mServiceTable = mClient.getTable(HighScore.class);
        } catch (MalformedURLException e) {
            Log.d(TAG, "can't connect");
        }
    }

    private static DBContextSV instance;

    public static DBContextSV getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new DBContextSV(context);
    }

    public void addNewHighScore(HighScore item) {
        mServiceTable.insert(item, new TableOperationCallback<HighScore>() {
            public void onCompleted(HighScore entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    Log.d(TAG, "added");
                } else {
                    // Insert failed
                    Log.d(TAG, "insert failed");
                }
            }
        });
    }

    public List<HighScore> getHighscorebyUserID(String userID) throws ExecutionException, InterruptedException {
        return mServiceTable.where().field("userId").
                eq(userID).execute().get();
    }

    public void settingThingsUp(String userID) {
        Log.d(TAG, "beginSetting1");
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<HighScore> highScore = getHighscorebyUserID(userID);
                    tmpListHighScore = highScore;
                    Log.d(TAG, "beginSetting2");
                    for (HighScore tmpHighScore : highScore) {
//                        Log.d(TAG, tmpHighScore.toString());
                        ManagerPreference.getInstance().putLevel(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getLevel());
                        ManagerPreference.getInstance().putExpCurrent(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getExpCurrent());
                        ManagerPreference.getInstance().putScore(tmpHighScore.getType(), tmpHighScore.getPosition(), tmpHighScore.getHighscore());

                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        runAsyncTask(task);


    }

    public void updateHighScore(String userID, String type, int positon, int nExp, int nLvl, int nHighScore) throws ExecutionException, InterruptedException {
        Log.d(TAG, "begin Update");
        for(HighScore highScore:tmpListHighScore){
            if(highScore.getType().equals(type) && highScore.getPosition() == positon){
                highScore.setExpCurrent(nExp);
                highScore.setHighscore(nHighScore);
                highScore.setLevel(nLvl);

                AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            mServiceTable.update(highScore).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                };

                runAsyncTask(task);

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
