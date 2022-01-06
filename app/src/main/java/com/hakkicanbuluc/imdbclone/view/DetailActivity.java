package com.hakkicanbuluc.imdbclone.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hakkicanbuluc.imdbclone.R;
import com.hakkicanbuluc.imdbclone.model.OpenModel;
import com.hakkicanbuluc.imdbclone.model.User;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    OpenModel openModel;
    TextView txtTitle, txtReleased, txtRunTime, txtCategories, txtDirector,
            txtWriter, txtActors, txtSummary, txtRating, txtVotes, txtType;
    ImageView imagePoster, imageFavorite;
    ConstraintLayout layout;
    DocumentReference userDoc;
    User user;
    boolean isThereImdbID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        openModel = (OpenModel) intent.getSerializableExtra("openModel");

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userDoc = firestore.collection("Users").document(currentUser.getUid());
        userDoc.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    user = User.fromMap(documentSnapshot.getData());
                    if (openModel.getType().equals("series")) {
                        isThereImdbID = search(1, openModel.getImdbID());
                    } else
                    isThereImdbID = search(0, openModel.getImdbID());
                    if (isThereImdbID) imageFavorite.setImageResource(android.R.drawable.btn_star_big_on);
                    layout.setVisibility(View.VISIBLE);
                }
            } else {
                Log.d("UserDoc.get.onComplete.task.isNotSuccessful", task.getException().
                        toString());
            }
        });

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
        imageFavorite = findViewById(R.id.imageFavorite);

        layout = findViewById(R.id.layout);
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
        else
        type += openModel.getType();
        txtType.setText(type);

        Picasso.get()
                .load(openModel.getPosterUrl())
                .into(imagePoster);

        layout.setVisibility(View.INVISIBLE);
    }

    private boolean search(int type, String imdbID) {
        if (type == 1) {
            for (String tvSeriesID : user.getFavoriteTvSeries()) {
                if (tvSeriesID.equals(imdbID)) return true;
            }
        } else {
            for (String movieID : user.getFavoriteMovies()) {
                if (movieID.equals(imdbID)) return true;
            }
        }
        return false;
    }

    public void doFavorite(View view) {
        doTask(openModel.getType().equals("movie"), !isThereImdbID);
    }

    public void doTask(boolean isMovie, boolean isAdd) {
        Task task;
        String message, addStr;
        if (isAdd) {
            addStr = "added";
            if (isMovie) {
                task = userDoc.update("Favorite Movies", FieldValue.arrayUnion(openModel
                        .getImdbID()));
                message = "Movie";
            } else {
                task = userDoc.update("Favorite Tv Series", FieldValue.arrayUnion(openModel.
                        getImdbID()));
                message = "Tv Series";
            }
        } else {
            addStr = "removed";
            if (isMovie) {
                task = userDoc.update("Favorite Movies", FieldValue.arrayRemove(openModel
                        .getImdbID()));
                message = "Movie";
            } else {
                task = userDoc.update("Favorite Tv Series", FieldValue.arrayRemove(openModel
                        .getImdbID()));
                message = "Tv Series";
            }
        }

        task.addOnSuccessListener((OnSuccessListener<Void>) unused -> {
            Toast.makeText(
                    DetailActivity.this, openModel.getTitle() + " " + addStr + " to your" +
                            " favorite " + message + " list", Toast.LENGTH_LONG).show();
            imageFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            isThereImdbID = !isThereImdbID;
        }).addOnFailureListener(e ->
                Toast.makeText(DetailActivity.this, openModel.getTitle() + " could not be " +
                                addStr + " to your favorite " + message + " list\n" + e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show());
    }
}