package com.example.windzlord.x_lab6.services;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;

import com.example.windzlord.x_lab6.constant.Constant;
import com.example.windzlord.x_lab6.jsonmodels.QuoteJSONModel;
import com.example.windzlord.x_lab6.managers.DBContextRealm;
import com.example.windzlord.x_lab6.managers.DBContextSQLite;
import com.example.windzlord.x_lab6.managers.FileManager;
import com.example.windzlord.x_lab6.managers.Preferences;
import com.example.windzlord.x_lab6.models.Quote;
import com.example.windzlord.x_lab6.models.QuoteByRealm;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

import static com.example.windzlord.x_lab6.constant.Constant.QUOTE_API;

/**
 * Created by WindzLord on 10/23/2016.
 */

public class UnplashDownloadService extends IntentService {

    public UnplashDownloadService() {
        super("UnplashDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Download data by Retrofit
        if (true) {
            Retrofit quoteRetrofit = new Retrofit
                    .Builder()
                    .baseUrl("http://quotesondesign.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            QuoteService quoteService = quoteRetrofit.create(QuoteService.class);
            quoteService.getListQuotes().enqueue(new retrofit2.Callback<List<QuoteJSONModel>>() {
                @Override
                public void onResponse(retrofit2.Call<List<QuoteJSONModel>> call, retrofit2.Response<List<QuoteJSONModel>> response) {
                    System.out.println("onResponse Retrofit");
                    List<QuoteJSONModel> quoteJSONModels = response.body();

                    DBContextRealm.getInstance().deleteAllQuotes();
                    for (QuoteJSONModel quote : quoteJSONModels) {
                        DBContextRealm.getInstance().add(
                                QuoteByRealm.create(quote.getTitle(), quote.getContent())
                        );
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<QuoteJSONModel>> call, Throwable t) {

                }
            });
            // DONE
        } else
            new OkHttpClient()
                    .newCall(new Request.Builder()
                            .url(QUOTE_API)
                            .build())
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            System.out.println("onResponse");
                            DBContextSQLite.getInstance().clearAllRecords();
                            String bodyString = response.body().string();
                            QuoteJSONModel[] quotes = new Gson().fromJson(bodyString, QuoteJSONModel[].class);

                            for (QuoteJSONModel quoteJSONModel : quotes) {
                                Quote quote = new Quote(quoteJSONModel);
                                DBContextSQLite.getInstance().insert(quote);
                            }
                            System.out.println("Done Quotes");
                        }
                    });

        for (int i = 0; i < Constant.NUMBER; i++) {
            if (!Constant.running) {
                Constant.running = true;
                break;
            }
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(Constant.UNSPLASH_API);
            if (bitmap == null) {
                i--;
                continue;
            }

            System.out.println("Loading image: " + (i + 1));
            FileManager.getInstance().createImage(bitmap, i);

            Preferences.getInstance().putImageCount(i + 1);
        }
    }
}
