package com.udacity.oliverh.movies.ui.MainActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final MovieRepository mRepository;
    private final LiveData<List<Movie>> mMovies;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        mRepository = MovieRepository.getInstance(database);
        mMovies = mRepository.getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return mMovies;
    }
}
