package com.hakkicanbuluc.imdbclone.service;

import com.hakkicanbuluc.imdbclone.model.OpenMovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenMovieAPI {
    //http://www.omdbapi.com/?apikey=788a37c5&t=Dune

    @GET("?apikey=788a37c5")
    Call<OpenMovieModel> getData(@Query("t") String movieName);
}
