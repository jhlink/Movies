package com.udacity.oliverh.movies.model;

import java.util.Date;

public class Movie {
    private String mTitle;
    private Date mReleaseDate;
    private String mMoviePoster;
    private double mVoteAverage;
    private String mPlotSynopsis;

    public Movie () { }

    public Movie ( String title,
                   Date releaseDate,
                   String moviePoster,
                   double voteAverage,
                   String plotSynopsis ) {
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mMoviePoster = moviePoster;
        this.mVoteAverage = voteAverage;
        this.mPlotSynopsis = plotSynopsis;
    }

    public String getMoviePoster() {
        return mMoviePoster;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.mPlotSynopsis = plotSynopsis;
    }
}
