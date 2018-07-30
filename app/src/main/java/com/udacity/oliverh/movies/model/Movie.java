package com.udacity.oliverh.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.Date;

public class Movie implements Parcelable {
    private String title;
    private Date release_date;
    @Json(name = "poster_path")
    private String posterPath;
    private double vote_average;
    private String overview;

    public Movie(String title,
                 Date releaseDate,
                 String moviePoster,
                 double voteAverage,
                 String plotSynopsis) {
        this.title = title;
        this.release_date = releaseDate;
        this.posterPath = moviePoster;
        this.vote_average = voteAverage;
        this.overview = plotSynopsis;
    }

    private Movie(Parcel in) {
        title = in.readString();
        release_date = new Date(in.readLong());
        posterPath = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeLong(release_date.getTime());
        parcel.writeString(posterPath);
        parcel.writeDouble(vote_average);
        parcel.writeString(overview);
    }

    //  Transient modifer is used in context of Moshi to ignore emitting the CREATOR member property.
    public transient static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String newPath) {
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
