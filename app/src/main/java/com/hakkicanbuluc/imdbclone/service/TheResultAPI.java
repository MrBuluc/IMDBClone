package com.hakkicanbuluc.imdbclone.service;

import com.hakkicanbuluc.imdbclone.model.TheResultModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TheResultAPI {
    //https://api.themoviedb.org/3/movie/popular?api_key=a3c55ea01f37b3c34f8b15b6a244f40b

    @GET("movie/popular?api_key=a3c55ea01f37b3c34f8b15b6a244f40b")
    Observable<TheResultModel> getMovieData();

    @GET("tv/popular?api_key=a3c55ea01f37b3c34f8b15b6a244f40b")
    Observable<TheResultModel> getTvSeriesData();
}
