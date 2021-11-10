package com.hakkicanbuluc.imdbclone.service;

import com.hakkicanbuluc.imdbclone.model.OpenModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenAPI {
    //http://www.omdbapi.com/?apikey=788a37c5&t=Dune

    @GET("?apikey=788a37c5")
    Observable<OpenModel> getData(@Query("t") String movieName);
}
