package com.udacity.oliverh.movies.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.udacity.oliverh.movies.AppExecutors;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.database.MovieDao;
import com.udacity.oliverh.movies.data.database.QueriedMovieList;
import com.udacity.oliverh.movies.data.network.RepositoryResponse;
import com.udacity.oliverh.movies.data.network.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.data.network.MovieServiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    private static MovieRepository sInstance;
    private final AppDatabase mDb;
    private MovieDao mMovieDao;
    private MediatorLiveData<List<Movie>> mFavoriteMovies;

    private MovieRepository(final AppDatabase database) {
        mDb = database;
        mMovieDao = mDb.movieDao();
        mFavoriteMovies = new MediatorLiveData<>();

        mFavoriteMovies.addSource(mDb.movieDao().loadAllFavoriteMovies(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mFavoriteMovies.postValue(movies);
            }
          });
    }

    public static MovieRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (MovieRepository.class) {
                if (sInstance == null) {
                    sInstance = new MovieRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<RepositoryResponse> getFavoriteMovies() {
        MutableLiveData<RepositoryResponse> repoResponseData = new MutableLiveData<>();

        if ( mFavoriteMovies.getValue() == null ) {
            mFavoriteMovies.setValue(new ArrayList<Movie>());
        }

        RepositoryResponse databaseResponse = new RepositoryResponse(mFavoriteMovies.getValue());
        repoResponseData.postValue(databaseResponse);

        return repoResponseData;
    }

    public LiveData<Movie> getMovie(final int movieId) {
        return mMovieDao.getMovieById(movieId);
    }

    public void insertMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insertMovie(movie);
            }
        });
    }

    public void deleteMovie(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.deleteMovie(movie);
            }
        });
    }

    public LiveData<RepositoryResponse> getPopularMovies(final Context context) {
        Log.d(TAG, "Execute API request for PopularMovies list");
        Call topRatedMovieCall = MovieServiceAPI.getTopRatedMovies(context);
        return getMovies(topRatedMovieCall);
    }

    public LiveData<RepositoryResponse> getTopRatedMovies(final Context context) {
        Log.d(TAG, "Execute API request for TopRatedMovie list");
        Call topRatedMovieCall = MovieServiceAPI.getTopRatedMovies(context);
        return getMovies(topRatedMovieCall);
    }

    private LiveData<RepositoryResponse> getMovies(final Call apiCall) {
        final MutableLiveData<RepositoryResponse> movieApiResponse = new MutableLiveData<>();

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                apiCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "-- API Request[Fail]: " + e.getMessage());
                        movieApiResponse.postValue(new RepositoryResponse(e));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "-- API Request[Success]");
                            final QueriedMovieList movies = jsonListParser(response.body().string());
                            RepositoryResponse successfulResponse = new RepositoryResponse(movies.getResults());
                            movieApiResponse.postValue(successfulResponse);
                        }
                    }
                });
            }
        });

        return movieApiResponse;
    }

    private QueriedMovieList jsonListParser(String jsonResponse) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();

        JsonAdapter<QueriedMovieList> jsonAdapter = moshi.adapter(QueriedMovieList.class);

        return jsonAdapter.fromJson(jsonResponse);
    }
}
