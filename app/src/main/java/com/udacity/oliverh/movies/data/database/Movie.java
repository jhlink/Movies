package com.udacity.oliverh.movies.data.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie implements Parcelable {
    private String title;

    @Json(name = "release_date")
    private Date releaseDate;

    @Json(name = "poster_path")
    private String posterPath;

    @Json(name = "vote_average")
    private float voteAverage;
    private String overview;

    public Movie(String title,
                 Date releaseDate,
                 String moviePoster,
                 float voteAverage,
                 String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = moviePoster;
        this.voteAverage = voteAverage;
        this.overview = plotSynopsis;
    }

    private Movie(Parcel in) {
        title = in.readString();
        releaseDate = new Date(in.readLong());
        posterPath = in.readString();
        voteAverage = in.readFloat();
        overview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeLong(releaseDate.getTime());
        parcel.writeString(posterPath);
        parcel.writeFloat(voteAverage);
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
        return releaseDate;
    }

    public String getFormattedDate() {
        SimpleDateFormat movieDetailFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        return movieDetailFormat.format(releaseDate);
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPlotSynopsis() {
        return overview;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.overview = plotSynopsis;
    }
}
