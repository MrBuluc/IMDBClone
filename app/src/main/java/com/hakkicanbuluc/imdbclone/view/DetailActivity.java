package com.hakkicanbuluc.imdbclone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.model.OpenModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    OpenModel movie;
    TextView txtTitle, txtReleased, txtRunTime, txtCategories, txtDirector,
            txtWriter, txtActors, txtSummary;
    ImageView imagePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        movie = (OpenModel) intent.getSerializableExtra("movie");

        bindingViews();

        setting();



    }

    private void bindingViews() {
        txtTitle = findViewById(R.id.txtTitle);
        txtReleased = findViewById(R.id.txtReleased);
        txtRunTime = findViewById(R.id.txtRunTime);
        txtCategories = findViewById(R.id.txtCategories);
        txtDirector = findViewById(R.id.txtDirector);
        txtWriter = findViewById(R.id.txtWriter);
        txtActors = findViewById(R.id.txtActors);
        txtSummary = findViewById(R.id.txtSummary);

        imagePoster = findViewById(R.id.imagePoster);
    }

    private void setting() {
        txtTitle.setText(movie.getTitle());
        txtReleased.setText(movie.getReleased());
        txtRunTime.setText(movie.getRunTime());
        txtCategories.setText(movie.getCategories());
        txtDirector.setText(movie.getDirector());
        txtWriter.setText(movie.getWriter());
        txtActors.setText(movie.getActors());
        txtSummary.setText(movie.getSummary());

        Picasso.get()
                .load(movie.getPosterUrl())
                .into(imagePoster);
    }
}