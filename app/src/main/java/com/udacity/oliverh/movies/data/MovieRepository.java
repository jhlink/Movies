package com.udacity.oliverh.movies.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.udacity.oliverh.movies.AppExecutors;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.database.MovieDao;
import com.udacity.oliverh.movies.data.database.QueriedMovieList;
import com.udacity.oliverh.movies.data.network.ApiResponse;
import com.udacity.oliverh.movies.data.network.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.data.network.MovieServiceAPI;
import com.udacity.oliverh.movies.ui.MainActivity.MainActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    private static MovieRepository sInstance;
    private final AppDatabase mDb;
    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllFavoriteMovies;

    private MovieRepository(final AppDatabase database) {
        mDb = database;
        mMovieDao = mDb.movieDao();
        mAllFavoriteMovies = mMovieDao.loadAllFavoriteMovies();
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

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mAllFavoriteMovies;
    }

    public LiveData<Movie> getMovie(final int movieId) {
        return mMovieDao.getMovieById(movieId);
    }

    public void insert(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insertMovie(movie);
            }
        });
    }

    public LiveData<ApiResponse> getPopularMovies(final Context context) {

        Log.d(TAG, "Execute API request for PopularMovies list");

        final MutableLiveData<ApiResponse> movieApiResponse = new MutableLiveData<>();

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call popularMoviesCall = MovieServiceAPI.getPopularMovies(context);
                popularMoviesCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "API request failed | " + e.getMessage());
                        movieApiResponse.postValue(new ApiResponse(e));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "API request successful");
                            final QueriedMovieList movies = jsonListParser(response.body().string());
                            ApiResponse successfulResponse = new ApiResponse(movies.getResults());
                            movieApiResponse.postValue(successfulResponse);
                        }
                    }
                });
            }
        });

        return movieApiResponse;
    }

    public LiveData<ApiResponse> getTopRatedMovies(final Context context) {

        Log.d(TAG, "Execute API request for TopRatedMovie list");

        final MutableLiveData<ApiResponse> movieApiResponse = new MutableLiveData<>();

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call topRatedMovieCall = MovieServiceAPI.getTopRatedMovies(context);
                topRatedMovieCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d(TAG, "API request failed | " + e.getMessage());
                        movieApiResponse.postValue(new ApiResponse(e));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "API request successful");
                            final QueriedMovieList movies = jsonListParser(response.body().string());
                            ApiResponse successfulResponse = new ApiResponse(movies.getResults());
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
