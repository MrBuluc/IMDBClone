package com.hakkicanbuluc.imdbclone.service;

import com.hakkicanbuluc.imdbclone.model.OpenModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMovieAPI {
    //http://www.omdbapi.com/?apikey=788a37c5&t=Dune

    @GET("?apikey=788a37c5")
    Call<OpenModel> getData(@Query("t") String movieName);
}
