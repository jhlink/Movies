package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.AppDatabase;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieRepository movieRepository;
    private final int mMovieId;

    public MovieDetailsViewModelFactory(AppDatabase database, int movieId){
        movieRepository = MovieRepository.getInstance(database);
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(movieRepository);

    }
}
