package com.udacity.oliverh.movies.ui.MainActivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.network.RepositoryResponse;

public class MainActivityViewModel extends AndroidViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private final MovieRepository mRepository;
    private final MediatorLiveData<RepositoryResponse> movieApiResponse;
    private LiveData<RepositoryResponse> previousLiveData;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        mRepository = MovieRepository.getInstance(database);
        movieApiResponse = new MediatorLiveData<>();
        previousLiveData = null;
        fetchTopRatedMovies(application.getApplicationContext());
    }

    public void fetchTopRatedMovies(Context mContext) {
        Log.d(TAG, "Fetch TopRatedMovies -> MovieRepository");
        removePreviousSource();
        previousLiveData = mRepository.getTopRatedMovies(mContext);
        setSourceForLiveData(previousLiveData);
    }

    public void fetchPopularMovies(Context mContext) {
        Log.d(TAG, "Fetch PopularMovies -> MovieRepository");
        removePreviousSource();
        previousLiveData = mRepository.getPopularMovies(mContext);
        setSourceForLiveData(previousLiveData);
    }

    private void setSourceForLiveData(final LiveData<RepositoryResponse> movieList) {
        movieApiResponse.addSource(movieList,
                new Observer<RepositoryResponse>() {
                    @Override
                    public void onChanged(@Nullable RepositoryResponse apiResponse) {
                        Log.d(TAG, "Updated movie list");
                        movieApiResponse.setValue(apiResponse);
                    }
                });
    }

    private void removePreviousSource() {
        if ( previousLiveData == null ) {
            return;
        }
        movieApiResponse.removeSource(previousLiveData);
    }

    public LiveData<RepositoryResponse> getData() {
        Log.d(TAG, "Get movieList Data");
        return movieApiResponse;
    }

    public void fetchFavoriteMovies() {
        Log.d(TAG, "Fetch FavoriteMovies -> MovieRepository");
        removePreviousSource();
        previousLiveData = mRepository.getFavoriteMovies();
        setSourceForLiveData(previousLiveData);
    }
}
