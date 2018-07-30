package com.udacity.oliverh.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.udacity.oliverh.movies.databinding.MovieDetailsBinding;
import com.udacity.oliverh.movies.model.Movie;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.movie_details);

        Intent activityInitiatingIntent = getIntent();

        if (activityInitiatingIntent.hasExtra("MOVIE_DATA")) {
            Movie movieData = activityInitiatingIntent.getParcelableExtra("MOVIE_DATA");

            binding.setVariable(movie, movieData);
        }
    }
}
