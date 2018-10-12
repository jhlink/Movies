package com.udacity.oliverh.movies.ui.MainActivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.Movie;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private final MovieRepository mRepository;
    private final LiveData<List<Movie>> mMovies;

    public MainActivityViewModel(MovieRepository repository) {
        mRepository = repository;
        mMovies = mRepository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mMovies;
    }
}
