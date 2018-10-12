package com.udacity.oliverh.movies.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.udacity.oliverh.movies.AppExecutors;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.database.MovieDao;

import java.util.List;

public class MovieRepository {

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

    LiveData<List<Movie>> getAllFavoriteMovies() {
        return mAllFavoriteMovies;
    }

    public void insert(final Movie movie) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insertMovie(movie);
            }
        });
    }
}
