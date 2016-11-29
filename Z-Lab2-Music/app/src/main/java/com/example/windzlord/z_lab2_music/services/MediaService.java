package com.example.windzlord.z_lab2_music.services;

import com.example.windzlord.z_lab2_music.models.MediaTypeJSONModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by WindzLord on 11/29/2016.
 */

public interface MediaService {
    @GET("/data/media-types.json")
    Call<List<MediaTypeJSONModel>> getMediaTypeList();
}
