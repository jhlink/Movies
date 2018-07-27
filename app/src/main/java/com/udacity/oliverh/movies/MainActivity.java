package com.udacity.oliverh.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.udacity.oliverh.movies.model.Movie;
import com.udacity.oliverh.movies.utilities.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.utilities.MovieServiceAPI;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int NUM_MOVIE_GRID_ITEMS = 20;
    private static final int NUM_MOVIE_GRID_SPAN_COUNT = 3;
    private MovieAdapter mAdapter;
    private RecyclerView mMovieGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGrid = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_MOVIE_GRID_SPAN_COUNT);
        mMovieGrid.setLayoutManager(layoutManager);

        mMovieGrid.setHasFixedSize(true);

        mAdapter = new MovieAdapter(NUM_MOVIE_GRID_ITEMS);

        mMovieGrid.setAdapter(mAdapter);

        try {
            // Need to Create a Singleton helper class to ensure that OkHTTPClient is only
            //  instantiated once.
            new MovieServiceAPI().getPopularMovies(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test() throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();
        JsonAdapter<Movie> jsonAdapter = moshi.adapter(Movie.class);

        Log.i("MOSHI", String.valueOf(getString(R.string.jsontest)));
        Movie testVal = jsonAdapter.fromJson(getString(R.string.jsontest));
        Log.i("MOSHI", testVal.toString());
    }
}