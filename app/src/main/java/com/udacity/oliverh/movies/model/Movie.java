package com.udacity.oliverh.movies.model;

import java.util.Date;

public class Movie {
    private String title;
    private Date release_date;
    private String poster_path;
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
        this.poster_path = moviePoster;
        this.vote_average = voteAverage;
        this.overview = plotSynopsis;
    }

    public String getMoviePoster() {
        return poster_path;
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
