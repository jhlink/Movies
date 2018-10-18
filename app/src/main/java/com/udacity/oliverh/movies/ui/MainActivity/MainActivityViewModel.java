package com.udacity.oliverh.movies.ui.MainActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.network.ApiResponse;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final MovieRepository mRepository;
    //private final LiveData<List<Movie>> mMovies;
    private final MediatorLiveData<ApiResponse> movieApiResponse;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        mRepository = MovieRepository.getInstance(database);
        movieApiResponse = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse> getTopRatedMovies() {
        movieApiResponse.addSource(mRepository.getTopRatedMovies(this.getApplication().getApplicationContext()),
                new Observer<ApiResponse>() {
                    @Override
                    public void onChanged(@Nullable ApiResponse apiResponse) {
                        movieApiResponse.setValue(apiResponse);
                    }
                });
        return movieApiResponse;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return null;
    }
}
