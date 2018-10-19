package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsBinding binding;
    private Movie movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.movie_details);

        Intent activityInitiatingIntent = getIntent();

        mDb = AppDatabase.getInstance(getApplicationContext());

        String parcelTag = getString(R.string.ParcelID);
        if (activityInitiatingIntent.hasExtra(parcelTag)) {
            movieData = activityInitiatingIntent.getParcelableExtra(parcelTag);

            setupViewModel(movieData.getId());

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
            insertMovie(movieData);
        }
    }

    private void insertMovie(Movie iMovie) {
        movieDetailsViewModel.insertMovie(iMovie);
    }

    private void setupViewModel(int movieId) {
        MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(mDb, movieId);
        movieDetailsViewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                if (movie == null) {
                    binding.favoriteBtn.setChecked(false);
                }
                binding.favoriteBtn.setChecked(true);
                movieDetailsViewModel.getMovie().removeObserver(this);
            }
        });
    }
}

