package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.Movie;

public class MovieDetailsViewModel extends ViewModel {
    private final MovieRepository mRepository;
    private final LiveData<Movie> movie;

    MovieDetailsViewModel(MovieRepository repository, int movieId) {
        mRepository = repository;
        movie = mRepository.getMovie(movieId);
    }

    public LiveData<Movie> getMovie(int movieID) {
        return movie;
    }
}
