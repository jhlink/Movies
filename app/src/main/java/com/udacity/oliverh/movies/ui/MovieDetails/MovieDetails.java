package com.udacity.oliverh.movies.ui.MovieDetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.databinding.MovieDetailsBinding;
import com.udacity.oliverh.movies.data.database.Movie;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.movie_details);

        Intent activityInitiatingIntent = getIntent();

        String parcelTag = getString(R.string.ParcelID);
        if (activityInitiatingIntent.hasExtra(parcelTag)) {
            Movie movieData = activityInitiatingIntent.getParcelableExtra(parcelTag);
            binding.setVariable(movie, movieData);
            binding.executePendingBindings();
        }
    }
}

