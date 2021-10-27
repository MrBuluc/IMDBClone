package com.hakkicanbuluc.imdbclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.model.TheMovieModel;
import com.hakkicanbuluc.imdbclone.model.TheResultModel;
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

    List<TheMovieModel> movies = new ArrayList<>(), openMovies = new ArrayList<>();
    private String THERESULTBASEURL = "https://api.themoviedb.org/3/";
    private  String THEMOVIEBASEURL = "http://www.omdbapi.com/";
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(THERESULTBASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadMovies();

        retrofit = new Retrofit.Builder()
                .baseUrl(THEMOVIEBASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadOpenMovies();

    }

    private void loadOpenMovies() {

    }

    private void loadMovies() {
        TheResultAPI theResultAPI = retrofit.create(TheResultAPI.class);
        Call<TheResultModel> call = theResultAPI.getData();
        call.enqueue(new Callback<TheResultModel>() {
            @Override
            public void onResponse(@NonNull Call<TheResultModel> call, @NonNull Response<TheResultModel> response) {
                if (response.isSuccessful()) {
                    TheResultModel theResultModel = response.body();
                    List<HashMap> theResultModelResults = theResultModel.getResults();
                    for (HashMap map : theResultModelResults) {
                        TheMovieModel theMovieModel = TheMovieModel.fromMap(map);
                        movies.add(theMovieModel);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TheResultModel> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
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