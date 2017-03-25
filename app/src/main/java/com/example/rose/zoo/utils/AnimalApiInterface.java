package com.example.rose.zoo.utils;

import com.example.rose.zoo.models.Animal;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Rose on 12/27/2016.
 */

public interface AnimalApiInterface {
    @GET("exhibits.json")
    Call<List<Animal>> getStreams();
}
