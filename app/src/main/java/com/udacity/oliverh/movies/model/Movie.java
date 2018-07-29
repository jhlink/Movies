package com.udacity.oliverh.movies.model;

import android.util.Log;

import com.squareup.moshi.Json;
import com.udacity.oliverh.movies.utilities.MovieServiceAPI;

import java.util.Date;

public class Movie {
    private String title;
    private Date release_date;
    @Json(name = "poster_path") private String posterPath;
    private double vote_average;
    private String overview;

    public Movie () { }

    public Movie ( String title,
                   Date releaseDate,
                   String moviePoster,
                   double voteAverage,
                   String plotSynopsis ) {
        this.title = title;
        this.release_date = releaseDate;
        this.posterPath = moviePoster;
        this.vote_average = voteAverage;
        this.overview = plotSynopsis;

    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath( String newPath ) {
        this.posterPath = newPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(Date releaseDate) {
        this.release_date = releaseDate;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(double voteAverage) {
        this.vote_average = voteAverage;
    }

    public String getPlotSynopsis() {
        return overview;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.overview = plotSynopsis;
    }
}
