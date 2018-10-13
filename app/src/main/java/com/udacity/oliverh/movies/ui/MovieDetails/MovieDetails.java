package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.databinding.MovieDetailsBinding;
import com.udacity.oliverh.movies.data.database.Movie;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieDetails extends AppCompatActivity {

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

    private void setupViewModelFactory(int movieId) {
        MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(mDb, movieId);
        final MovieDetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);

        viewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                viewModel.getMovie().removeObserver(this);
                // Use binding to set live updates?
            }
        });
    }


}

