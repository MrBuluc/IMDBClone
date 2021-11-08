package com.hakkicanbuluc.imdbclone.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OpenModel implements Serializable {

    @SerializedName("Title")
    String title;
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
    @SerializedName("Response")
    String response;

    public boolean isNotNull() {
        return !response.equals("False");
    }

    public String getTitle() {
        return title;
    }

    public String getReleased() {
        return released;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getCategories() {
        return categories;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getSummary() {
        return summary;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getVotes() {
        return votes;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }
}
