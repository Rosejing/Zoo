package com.example.rose.zoo.utils;

import com.example.rose.zoo.models.Pin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

/**
 * Created by Rose on 01/04/2017.
 */

public interface PinsApiInterface {
    @GET( "Pins.JSON")
    Call<List<Pin>> getStreams();
}
