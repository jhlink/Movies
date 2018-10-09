package com.udacity.oliverh.movies.ui.MovieDetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.databinding.MovieDetailsBinding;
import com.udacity.oliverh.movies.data.database.Movie;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieDetails extends AppCompatActivity {

    private final static String MOVIE_DETAILS_TAG = MovieDetails.class.getSimpleName();
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.movie_details);

        Intent activityInitiatingIntent = getIntent();

        mDb = AppDatabase.getInstance(getApplicationContext());

        String parcelTag = getString(R.string.ParcelID);
        if (activityInitiatingIntent.hasExtra(parcelTag)) {
            Movie movieData = activityInitiatingIntent.getParcelableExtra(parcelTag);
            binding.setVariable(movie, movieData);
            binding.executePendingBindings();
        }
    }

    public void onClickFavoriteBtn(View view) {
        Log.d(MOVIE_DETAILS_TAG, "Button clicked.");
        if (view.isPressed()) {
            Log.d(MOVIE_DETAILS_TAG, "Button pressed state.");
        } else {
            Log.d(MOVIE_DETAILS_TAG, "Button default state.");
        }
    }

}

