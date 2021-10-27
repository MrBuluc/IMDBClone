package com.hakkicanbuluc.imdbclone.service;

import com.hakkicanbuluc.imdbclone.model.OpenMovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenMovieAPI {
    //http://www.omdbapi.com/?apikey=788a37c5&t=Dune

    @GET("?apikey=788a37c5&t={movieName}")
    Call<OpenMovieModel> getData(@Path("movieName") String movieName);
}
