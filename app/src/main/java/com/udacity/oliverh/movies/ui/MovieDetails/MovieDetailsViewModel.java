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

    public void insertMovie(Movie iMovie) {
        mRepository.insertMovie(iMovie);
    }

    public void deleteMovie(Movie iMovie) {
        mRepository.deleteMovie(iMovie);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }
}
