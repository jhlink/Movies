package com.udacity.oliverh.movies.data;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.udacity.oliverh.movies.AppExecutors;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.database.MovieDao;
import com.udacity.oliverh.movies.data.network.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.data.network.MoshiModels.GenericQueriedList;
import com.udacity.oliverh.movies.data.network.MovieServiceAPI;
import com.udacity.oliverh.movies.data.network.RepositoryResponse;
import com.udacity.oliverh.movies.data.network.Review;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    private static MovieRepository sInstance;
    private final AppDatabase mDb;
    private MovieDao mMovieDao;

    private MovieRepository(final AppDatabase database) {
        mDb = database;
        mMovieDao = mDb.movieDao();
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

        LiveData<List<Movie>> favoriteMovies = mMovieDao.loadAllFavoriteMovies();

        LiveData<RepositoryResponse> repoResponseData =
                Transformations.map(favoriteMovies, new Function<List<Movie>, RepositoryResponse>(){
                    @Override
                    public RepositoryResponse apply(List<Movie> movies) {
                        RepositoryResponse databaseResponse = new RepositoryResponse(movies);
                        return databaseResponse;
                    }
                });

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
        Call popularMovieCall = MovieServiceAPI.getPopularMovies(context);
        return getData(popularMovieCall, Movie.class);
    }

    public LiveData<RepositoryResponse> getTopRatedMovies(final Context context) {
        Log.d(TAG, "Execute API request for TopRatedMovie list");
        Call topRatedMovieCall = MovieServiceAPI.getTopRatedMovies(context);
        return getData(topRatedMovieCall, Movie.class);
    }

    private LiveData<RepositoryResponse> getData(final Call apiCall, final Type targetDataType) {
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
                            final GenericQueriedList data = genericJsonListParser(response.body().string(), targetDataType);
                            RepositoryResponse successfulResponse = new RepositoryResponse(data.getResults());
                            movieApiResponse.postValue(successfulResponse);
                        }
                    }
                });
            }
        });

        return movieApiResponse;
    }

    private GenericQueriedList genericJsonListParser(String jsonResponse, Type listType) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();

        Type typa = Types.newParameterizedType(GenericQueriedList.class, listType);
        JsonAdapter<GenericQueriedList> jsonAdapter = moshi.adapter(typa);

        return jsonAdapter.fromJson(jsonResponse);
    }

    public LiveData<RepositoryResponse> getMovieReviews(final Context context, int movieId) {
        Log.d(TAG, "Execute API request for movie review list");
        Call movieReviewsCall = MovieServiceAPI.getMovieReviews(context, movieId);
        return getData(movieReviewsCall, Review.class);
    }
}
