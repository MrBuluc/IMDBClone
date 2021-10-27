package com.hakkicanbuluc.imdbclone.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class OpenMovieModel {

    @SerializedName("Title")
    String title;
    @SerializedName("Year")
    String year;
    @SerializedName("Released")
    String released;
    @SerializedName("Runtime")
    String runTime;
    @SerializedName("Genre")
    String categories;
    @SerializedName("Director")
    String director;
    @SerializedName("Writer")
    String writer;
    @SerializedName("Actors")
    String actors;
    @SerializedName("Plot")
    String summary;
    @SerializedName("Poster")
    String posterUrl;
    @SerializedName("imdbRating")
    String rating;
    @SerializedName("imdbVotes")
    String votes;
    @SerializedName("Type")
    String type;

    @NonNull
    @Override
    public String toString() {
        return "OpenMovieModel{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
