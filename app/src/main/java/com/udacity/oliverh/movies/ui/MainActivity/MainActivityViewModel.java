package com.udacity.oliverh.movies.ui.MainActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.network.ApiResponse;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private final MovieRepository mRepository;
    private final MediatorLiveData<ApiResponse> movieApiResponse;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        mRepository = MovieRepository.getInstance(database);
        movieApiResponse = new MediatorLiveData<>();
        fetchTopRatedMovies(application.getApplicationContext());
    }

    public void fetchTopRatedMovies(Context mContext) {
        Log.d(TAG, "Fetch TopRatedMovies -> MovieRepository");
        fetchMovieList(mRepository.getTopRatedMovies(mContext));
    }

    public void fetchPopularMovies(Context mContext) {
        Log.d(TAG, "Fetch PopularMovies -> MovieRepository");
        fetchMovieList(mRepository.getPopularMovies(mContext));
    }

    private void fetchMovieList(LiveData<ApiResponse> movieList) {
        movieApiResponse.addSource(movieList,
                new Observer<ApiResponse>() {
                    @Override
                    public void onChanged(@Nullable ApiResponse apiResponse) {
                        Log.d(TAG, "Updated movie list");
                        movieApiResponse.setValue(apiResponse);
                    }
                });
    }

    public LiveData<ApiResponse> getData() {
        Log.d(TAG, "Get movieList Data");
        return movieApiResponse;
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return null;
    }
}
