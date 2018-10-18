package com.udacity.oliverh.movies.data.network;

import com.udacity.oliverh.movies.data.database.Movie;

import java.util.List;

public class ApiResponse {
    private List<Movie> movieList;
    private Throwable error;

    public ApiResponse(List<Movie> movies) {
        this.movieList = movies;
        this.error = null;
    }

    public ApiResponse(Throwable err) {
        this.movieList = null;
        this.error = err;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
