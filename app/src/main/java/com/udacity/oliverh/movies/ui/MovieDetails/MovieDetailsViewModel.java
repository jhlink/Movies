package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.udacity.oliverh.movies.data.MovieRepository;
import com.udacity.oliverh.movies.data.database.AppDatabase;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.network.RepositoryResponse;

public class MovieDetailsViewModel extends ViewModel {
    private final MovieRepository mRepository;
    private final LiveData<Movie> movie;
    private final LiveData<RepositoryResponse> reviews;
    private final LiveData<RepositoryResponse> videos;

MovieDetailsViewModel(Context context, int movieId) {
        AppDatabase database = AppDatabase.getInstance(context);
        mRepository = MovieRepository.getInstance(database);
        movie = mRepository.getMovie(movieId);
        reviews = mRepository.getMovieReviews(context, movieId);
        videos = mRepository.getMovieVideos(context, movieId);
    }

    public void insertMovie(Movie iMovie) {
        mRepository.insertMovie(iMovie);
    }

    public void deleteMovie(Movie iMovie) {
        mRepository.deleteMovie(iMovie);
    }

    public LiveData<Movie> getMovie() {
        return movie;
    }

    public LiveData<RepositoryResponse> getReviews() {
        return reviews;
    }

    public LiveData<RepositoryResponse> getVideos() {
        return videos;
    }
}
