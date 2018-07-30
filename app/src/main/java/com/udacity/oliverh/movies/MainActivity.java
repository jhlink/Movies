package com.udacity.oliverh.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.udacity.oliverh.movies.model.Movie;
import com.udacity.oliverh.movies.model.QueriedMovieList;
import com.udacity.oliverh.movies.utilities.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.utilities.MovieServiceAPI;
import com.udacity.oliverh.movies.utilities.Recycler.GridItemDecoration;
import com.udacity.oliverh.movies.utilities.Recycler.MovieAdapter;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.GridItemClickListener {
    private static final int NUM_MOVIE_GRID_SPAN_COUNT = 2;
    private MovieAdapter mAdapter;
    private RecyclerView mMovieGrid;
    private ProgressBar mProgressBar;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGrid = (RecyclerView) findViewById(R.id.rv_movies);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        GridItemDecoration gridItemStyle = new GridItemDecoration(spacingInPixels);
        mMovieGrid.addItemDecoration(gridItemStyle);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUM_MOVIE_GRID_SPAN_COUNT);
        mMovieGrid.setLayoutManager(layoutManager);
        mMovieGrid.setHasFixedSize(true);
        mAdapter = new MovieAdapter(this);
        mMovieGrid.setAdapter(mAdapter);

        showTopRatedMovies();
    }

    @Override
    public void onGridItemClick(Movie movie) {
        Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetails.class);
        String parcelTag = getString(R.string.ParcelID);
        movieDetailsIntent.putExtra(parcelTag, movie);
        startActivity(movieDetailsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();

        switch (selectedMenuItem) {
            case R.id.action_popular:
                showPopularMovies();
                return true;

            case R.id.action_top_rated:
                showTopRatedMovies();
                return true;

            default:
                showPopularMovies();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showTopRatedMovies() {
        Callback topRatedMoviesRequestCb =  getNetworkRequestCallback();

        try {
            onNetworkRequest();
            MovieServiceAPI.getTopRatedMovies(this, topRatedMoviesRequestCb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showPopularMovies() {
        Callback popularMoviesRequestCb = getNetworkRequestCallback();

        try {
            onNetworkRequest();
            MovieServiceAPI.getPopularMovies(this, popularMoviesRequestCb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onNetworkFailure() {
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mMovieGrid.setVisibility(View.INVISIBLE);
    }

    private void onNetworkRequest() {
        mErrorMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mMovieGrid.setVisibility(View.INVISIBLE);
    }

    private void onNetworkSuccess() {
        mMovieGrid.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private QueriedMovieList jsonListParser(String jsonResponse) throws IOException {
        Moshi moshi = new Moshi.Builder()
                .add(new DateAdapter())
                .build();

        JsonAdapter<QueriedMovieList> jsonAdapter = moshi.adapter(QueriedMovieList.class);

        QueriedMovieList result = jsonAdapter.fromJson(jsonResponse);
        return result;
    }

    private Callback getNetworkRequestCallback() {
        Callback networkCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onNetworkFailure();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final QueriedMovieList movies = jsonListParser(response.body().string());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onNetworkSuccess();
                        mAdapter.setMovieListData(movies.getResults());
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };

        return networkCallback;
    }
}