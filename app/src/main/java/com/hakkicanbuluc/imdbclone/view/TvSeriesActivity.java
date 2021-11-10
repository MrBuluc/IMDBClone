package com.hakkicanbuluc.imdbclone.view;

import static com.hakkicanbuluc.imdbclone.Const.OPENMOVIEBASEURL;
import static com.hakkicanbuluc.imdbclone.Const.THEMOVIEBASEURL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.adapter.RecyclerViewAdapter;
import com.hakkicanbuluc.imdbclone.model.OpenModel;
import com.hakkicanbuluc.imdbclone.model.TheResultModel;
import com.hakkicanbuluc.imdbclone.model.TheTvSeriesModel;
import com.hakkicanbuluc.imdbclone.service.OpenAPI;
import com.hakkicanbuluc.imdbclone.service.TheResultAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvSeriesActivity extends AppCompatActivity {
    List<TheTvSeriesModel> series = new ArrayList<>();
    ArrayList<OpenModel> openTvModels = new ArrayList<>();

    Retrofit retrofit;

    Gson gson;

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series);

        recyclerView = findViewById(R.id.tvRecyclerView);

        gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(THEMOVIEBASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadTvSeries();
    }

    private void loadTvSeries() {
        TheResultAPI theResultAPI = retrofit.create(TheResultAPI.class);

        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(theResultAPI.getTvSeriesData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::handleResponseTheResultModel));
    }

    private void handleResponseTheResultModel(TheResultModel theResultModel) {
        List<HashMap> theResultModelResults = theResultModel != null ? theResultModel.
                getResults() : null;
        if (theResultModelResults != null) {
            for (HashMap map : theResultModelResults) {
                TheTvSeriesModel theTvSeriesModel = TheTvSeriesModel.fromMap(map);
                series.add(theTvSeriesModel);
            }
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(OPENMOVIEBASEURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadOpenTvSeries();
    }

    private void loadOpenTvSeries() {
        OpenAPI openAPI = retrofit.create(OpenAPI.class);

        for (TheTvSeriesModel theTvSeriesModel : series) {
            compositeDisposable.add(openAPI.getData(theTvSeriesModel.getName())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponseOpenTvSeries));
        }
    }

    private void handleResponseOpenTvSeries(OpenModel openModel) {
        if (openModel.isNotNull()) {
            openTvModels.add(openModel);

            recyclerView.setLayoutManager(new LinearLayoutManager(TvSeriesActivity.
                    this));
            recyclerViewAdapter = new RecyclerViewAdapter(openTvModels);
            recyclerViewAdapter.setClickListener((index, view) -> {
                OpenModel openModel1 = openTvModels.get(index);
                Intent intent = new Intent(TvSeriesActivity.this, DetailActivity
                        .class);
                intent.putExtra("openModel", openModel1);
                startActivity(intent);
            });
            recyclerView.setAdapter(recyclerViewAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}