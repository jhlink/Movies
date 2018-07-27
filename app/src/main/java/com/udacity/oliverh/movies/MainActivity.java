package com.udacity.oliverh.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.udacity.oliverh.movies.model.Movie;
import com.udacity.oliverh.movies.model.QueriedMovieList;
import com.udacity.oliverh.movies.utilities.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.utilities.MovieServiceAPI;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

        showPopularMovies();

    }

    private void showPopularMovies() {
        Callback popularMoviesRequestCb = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                QueriedMovieList movies = jsonListParser( response.body().string() ) ;
            }
        };

        try {
            MovieServiceAPI.getPopularMovies(this, popularMoviesRequestCb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueriedMovieList jsonListParser(String jsonResponse) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();

        JsonAdapter<QueriedMovieList> jsonAdapter = moshi.adapter(QueriedMovieList.class);

        QueriedMovieList result = jsonAdapter.fromJson(jsonResponse);
        return result;
    }
}