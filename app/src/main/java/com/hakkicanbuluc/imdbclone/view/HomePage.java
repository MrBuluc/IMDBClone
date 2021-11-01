package com.hakkicanbuluc.imdbclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.model.OpenMovieModel;
import com.hakkicanbuluc.imdbclone.model.TheMovieModel;
import com.hakkicanbuluc.imdbclone.model.TheResultModel;
import com.hakkicanbuluc.imdbclone.service.OpenMovieAPI;
import com.hakkicanbuluc.imdbclone.service.TheResultAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {

    //https://api.themoviedb.org/3/movie/popular?api_key=a3c55ea01f37b3c34f8b15b6a244f40b
    //http://www.omdbapi.com/?apikey=788a37c5&t=Dune
    /*List<String> popularMovieList = new ArrayList<>();
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) resultMap.get("results");
        for (HashMap map : results) {
            String title = (String) map.get("title");
            popularMovieList.add(title);
        }*/

    List<TheMovieModel> movies = new ArrayList<>();
    List<OpenMovieModel> openMovies = new ArrayList<>();
    private String THEMOVIEBASEURL = "https://api.themoviedb.org/3/";
    private  String OPENMOVIEBASEURL = "https://www.omdbapi.com/";
    Retrofit retrofit;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(THEMOVIEBASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadMovies();

    }

    private void loadMovies() {
        TheResultAPI theResultAPI = retrofit.create(TheResultAPI.class);
        Call<TheResultModel> call = theResultAPI.getData();
        call.enqueue(new Callback<TheResultModel>() {
            @Override
            public void onResponse(@NonNull Call<TheResultModel> call, @NonNull Response<TheResultModel> response) {
                if (response.isSuccessful()) {
                    TheResultModel theResultModel = response.body();
                    List<HashMap> theResultModelResults = theResultModel != null ? theResultModel.getResults() : null;
                    if (theResultModelResults != null) {
                        for (HashMap map : theResultModelResults) {
                            TheMovieModel theMovieModel = TheMovieModel.fromMap(map);
                            movies.add(theMovieModel);
                        }
                    }
                    retrofit = new Retrofit.Builder()
                            .baseUrl(OPENMOVIEBASEURL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();
                    loadOpenMovies();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TheResultModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadOpenMovies() {
        OpenMovieAPI openMovieAPI = retrofit.create(OpenMovieAPI.class);
        for (TheMovieModel movie : movies) {
            Call<OpenMovieModel> call = openMovieAPI.getData(movie.getTitle());
            call.enqueue(new Callback<OpenMovieModel>() {
                @Override
                public void onResponse(Call<OpenMovieModel> call, Response<OpenMovieModel> response) {
                    if (response.isSuccessful()) {
                        OpenMovieModel openMovieModel = response.body();
                        if (openMovieModel.isNotNull()) {
                            openMovies.add(openMovieModel);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OpenMovieModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}