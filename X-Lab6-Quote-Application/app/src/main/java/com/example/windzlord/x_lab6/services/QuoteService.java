package com.example.windzlord.x_lab6.services;

import com.example.windzlord.x_lab6.jsonmodels.QuoteJSONModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by WindzLord on 11/4/2016.
 */

public interface QuoteService {
    @GET("/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=20")
    Call<List<QuoteJSONModel>> getListQuotes();
}
