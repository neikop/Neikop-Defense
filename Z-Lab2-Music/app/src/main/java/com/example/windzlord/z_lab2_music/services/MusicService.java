package com.example.windzlord.z_lab2_music.services;

import com.example.windzlord.z_lab2_music.models.json_models.MediaDaddy;
import com.example.windzlord.z_lab2_music.models.json_models.MediaTypeX;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by WindzLord on 11/29/2016.
 */

public interface MusicService {
    @GET("/data/media-types.json")
    Call<List<MediaTypeX>> getMediaTypeList();

    @GET("/us/rss/topSongs/limit=50/genre={id}/explicit=true/json")
    Call<MediaDaddy> getMediaDaddy(@Path("id") String id);
}
