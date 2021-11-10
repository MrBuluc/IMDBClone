package com.hakkicanbuluc.imdbclone.model;

import java.util.HashMap;

public class TheMovieModel {

    String title;

    public static TheMovieModel fromMap(HashMap map) {
        TheMovieModel theMovieModel = new TheMovieModel();
        theMovieModel.title = (String) map.get("title");
        return theMovieModel;
    }

    public String getTitle() {
        return title;
    }
}
