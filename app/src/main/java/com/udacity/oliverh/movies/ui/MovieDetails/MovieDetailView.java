package com.udacity.oliverh.movies.ui.MovieDetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.databinding.MovieDetailsBinding;
import com.udacity.oliverh.movies.data.database.Movie;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieDetailView extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final static String MOVIE_DETAILS_TAG = MovieDetailView.class.getSimpleName();
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
            binding.favoriteBtn.setOnCheckedChangeListener(this);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(MOVIE_DETAILS_TAG, "Button checked.");
        } else {
            Log.d(MOVIE_DETAILS_TAG, "Button unchecked.");
        }
    }
}

