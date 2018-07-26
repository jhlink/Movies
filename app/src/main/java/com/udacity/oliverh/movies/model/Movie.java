package com.udacity.oliverh.movies.model;

public class Movie {
    private String mTitle;
    private String mReleaseDate;
    private String mMoviePoster;
    private double mVoteAverage;
    private String mPlotSynopsis;

    public Movie () { }

    public Movie ( String title,
                   String releaseDate,
                   String moviePoster,
                   double voteAverage,
                   String plotSynopsis ) {
        this.mTitle = title;
        this.mReleaseDate = releaseDate;
        this.mMoviePoster = moviePoster;
        this.mVoteAverage = voteAverage;
        this.mPlotSynopsis = plotSynopsis;
    }

}
