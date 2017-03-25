package com.example.rose.zoo.utils;

import com.example.rose.zoo.models.GalleryImage;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Rose on 12/31/2016.
 */

public interface GalleryApiInterface {
    @GET("gallery.json")
    Call<List<GalleryImage>> getStreams();
}
