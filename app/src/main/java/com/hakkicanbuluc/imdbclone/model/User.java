package com.hakkicanbuluc.imdbclone.model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    String userID;
    String name;
    String lastname;
    String email;
    ArrayList<String> favoriteMovies;
    ArrayList<String> favoriteTvSeries;

    public User(String userID, String name, String lastname, String email) {
        this.userID = userID;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.favoriteMovies = new ArrayList<>();
        this.favoriteTvSeries = new ArrayList<>();
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserID", userID);
        map.put("Name", name);
        map.put("Lastname", lastname);
        map.put("Email", email);
        map.put("Favorite Movies", favoriteMovies);
        map.put("Favorite Tv Series", favoriteTvSeries);
        return  map;
    }
}
