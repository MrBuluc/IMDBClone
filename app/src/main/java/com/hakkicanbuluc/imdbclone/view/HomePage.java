package com.hakkicanbuluc.imdbclone.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.adapter.RecyclerViewAdapter;
import com.hakkicanbuluc.imdbclone.model.OpenModel;
import com.hakkicanbuluc.imdbclone.model.TheMovieModel;
import com.hakkicanbuluc.imdbclone.model.TheResultModel;
import com.hakkicanbuluc.imdbclone.service.OpenMovieAPI;
import com.hakkicanbuluc.imdbclone.service.TheResultAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity {

    //https://api.themoviedb.org/3/movie/popular?api_key=a3c55ea01f37b3c34f8b15b6a244f40b
    //http://www.omdbapi.com/?apikey=788a37c5&t=Dune

    List<TheMovieModel> movies = new ArrayList<>();
    ArrayList<OpenModel> openModels = new ArrayList<>();

    private final String THEMOVIEBASEURL = "https://api.themoviedb.org/3/";
    private final String OPENMOVIEBASEURL = "https://www.omdbapi.com/";

    Retrofit retrofit;

    Gson gson;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);

        gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(THEMOVIEBASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadMovies();
    }

    private void loadMovies() {
        TheResultAPI theResultAPI = retrofit.create(TheResultAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(theResultAPI.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponseTheResultModel));
    }

    private void handleResponseTheResultModel(TheResultModel theResultModel) {
        List<HashMap> theResultModelResults = theResultModel != null ? theResultModel.getResults() : null;
        if (theResultModelResults != null) {
            for (HashMap map : theResultModelResults) {
                TheMovieModel theMovieModel = TheMovieModel.fromMap(map);
                movies.add(theMovieModel);
            }
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(OPENMOVIEBASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadOpenMovies();
    }

    private void loadOpenMovies() {
        OpenMovieAPI openMovieAPI = retrofit.create(OpenMovieAPI.class);

        for (TheMovieModel movieModel : movies) {
            compositeDisposable.add(openMovieAPI.getData(movieModel.getTitle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseOpenMovie));
        }
    }

    private void handleResponseOpenMovie(@NonNull OpenModel openModel) {
        if (openModel.isNotNull()) {
            openModels.add(openModel);

            recyclerView.setLayoutManager(new LinearLayoutManager(HomePage.this));
            recyclerViewAdapter = new RecyclerViewAdapter(openModels);
            recyclerView.setAdapter(recyclerViewAdapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}