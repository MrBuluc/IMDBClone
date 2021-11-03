package com.hakkicanbuluc.imdbclone.model;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class TheMovieModel {

    String title;
    String overview;
    String releaseDate;
    Double voteAvg;

    public static TheMovieModel fromMap(HashMap map) {
        TheMovieModel theMovieModel = new TheMovieModel();
        theMovieModel.title = (String) map.get("title");
        theMovieModel.overview = (String) map.get("overview");
        theMovieModel.releaseDate = (String) map.get("release_date");
        theMovieModel.voteAvg = (Double) map.get("vote_average");
        return theMovieModel;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAvg() {
        return voteAvg;
    }

    @NonNull
    @Override
    public String toString() {
        return "TheMovieModel{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
