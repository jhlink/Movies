package com.udacity.oliverh.movies.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

   @Query("SELECT * FROM movie ORDER BY title ASC")
   LiveData<List<Movie>> loadAllFavoriteMovies();

   @Insert
   void insertMovie(Movie movie);

   @Query("SELECT * FROM movie WHERE id =:movieId")
   LiveData<Movie> getMovieById(int movieId);

   @Delete
   void deleteMovie(Movie movie);
}
