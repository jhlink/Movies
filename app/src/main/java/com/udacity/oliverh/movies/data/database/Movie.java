package com.udacity.oliverh.movies.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    @ColumnInfo(name = "release_date")
    @Json(name = "release_date")
    private Date releaseDate;

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "vote_average")
    @Json(name = "vote_average")
    private float voteAverage;

    @Json(name = "overview")
    private String plotSynopsis;

    @Ignore
    public Movie(String title,
                 Date releaseDate,
                 String posterPath,
                 float voteAverage,
                 String plotSynopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    public Movie(int id,
                 String title,
                 Date releaseDate,
                 String posterPath,
                 float voteAverage,
                 String plotSynopsis) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.plotSynopsis = plotSynopsis;
    }

    @Ignore
    private Movie(Parcel in) {
        title = in.readString();
        releaseDate = new Date(in.readLong());
        posterPath = in.readString();
        voteAverage = in.readFloat();
        plotSynopsis = in.readString();
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
        parcel.writeString(plotSynopsis);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }
}
