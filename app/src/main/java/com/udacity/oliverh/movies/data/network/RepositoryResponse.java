package com.udacity.oliverh.movies.data.network;

import com.udacity.oliverh.movies.data.database.Movie;

import java.util.ArrayList;
import java.util.List;

public class RepositoryResponse<T> {
    private List<T> movieList;
    private Throwable error;

    public RepositoryResponse(List<T> movies) {
        this.movieList = movies;
        this.error = null;
    }

    public RepositoryResponse(Throwable err) {
        this.movieList = new ArrayList<>();
        this.error = err;
    }

    public List<T> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<T> movieList) {
        this.movieList = movieList;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
