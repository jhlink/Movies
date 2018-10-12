package com.udacity.oliverh.movies.ui.MainActivity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.udacity.oliverh.movies.data.MovieRepository;

public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MovieRepository movieRepository;

    public MainActivityViewModelFactory(MovieRepository repository){
        movieRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MainActivityViewModel(movieRepository);

    }
}
