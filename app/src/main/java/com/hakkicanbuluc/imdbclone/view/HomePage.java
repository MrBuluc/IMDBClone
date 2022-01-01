package com.hakkicanbuluc.imdbclone.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.hakkicanbuluc.imdbclone.R;

public class HomePage extends AppCompatActivity {
    EditText editTxtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        editTxtSearch = findViewById(R.id.editTxtSearch);

    }

    public void goToPopularMovies(View view) {
        Intent intent = new Intent(this, PopularMovies.class);
        startActivity(intent);
    }

    public void goToPopularTv(View view) {
        Intent intent = new Intent(this, TvSeriesActivity.class);
        startActivity(intent);
    }

    public void goToFavoriteMovies(View view) {

    }

    public void goToFavoriteTv(View view) {
    }

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}