package com.hakkicanbuluc.imdbclone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String userID;
    private String name;
    private String lastname;
    private String email;
    private ArrayList<String> favoriteMovies;
    private ArrayList<String> favoriteTvSeries;

    public User(String userID, String name, String lastname, String email) {
        this.userID = userID;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.favoriteMovies = new ArrayList<>();
        this.favoriteTvSeries = new ArrayList<>();
    }

    public User(String userID, String name, String lastname, String email, ArrayList<String>
            favoriteMovies, ArrayList<String> favoriteTvSeries) {
        this.userID = userID;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.favoriteMovies = favoriteMovies;
        this.favoriteTvSeries = favoriteTvSeries;
    }

    public static User fromMap(Map<String, Object> map) {
        return new User(map.get("UserID").toString(), map.get("Name").toString(), map.get("Lastname")
                .toString(), map.get("Email").toString(), (ArrayList<String>) map.
                get("Favorite Movies"), (ArrayList<String>) map.get("Favorite Tv Series"));
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

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public ArrayList<String> getFavoriteTvSeries() {
        return favoriteTvSeries;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", favoriteMovies=" + favoriteMovies +
                ", favoriteTvSeries=" + favoriteTvSeries +
                '}';
    }
}
