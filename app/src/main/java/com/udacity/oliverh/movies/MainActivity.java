package com.udacity.oliverh.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        //Log.i("KEY", BuildConfig.MOVIE_API_KEY);
    }
}