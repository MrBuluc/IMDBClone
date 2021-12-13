package com.hakkicanbuluc.imdbclone.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.model.OpenModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    OpenModel openModel;
    TextView txtTitle, txtReleased, txtRunTime, txtCategories, txtDirector,
            txtWriter, txtActors, txtSummary, txtRating, txtVotes, txtType;
    ImageView imagePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        openModel = (OpenModel) intent.getSerializableExtra("openModel");

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
        txtRating = findViewById(R.id.txtRating);
        txtVotes = findViewById(R.id.txtVotes);
        txtType = findViewById(R.id.txtType);

        imagePoster = findViewById(R.id.imagePoster);
    }

    private void setting() {
        txtTitle.setText(openModel.getTitle());
        txtReleased.setText(openModel.getReleased());
        txtRunTime.setText(openModel.getRunTime());
        txtCategories.setText(openModel.getCategories());
        txtDirector.setText(openModel.getDirector());
        txtWriter.setText(openModel.getWriter());
        txtActors.setText(openModel.getActors());
        txtSummary.setText(openModel.getSummary());
        txtRating.setText(!openModel.getRating().equals("N/A") ? openModel.getRating() + "/10" :
                "");
        txtVotes.setText(openModel.getVotes());
        String type = "";
        if (openModel.getType().equals("series"))
            type += "Tv ";
        type += openModel.getType();
        txtType.setText(type);

        Picasso.get()
                .load(openModel.getPosterUrl())
                .into(imagePoster);
    }
}