package com.udacity.oliverh.movies.ui.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.udacity.oliverh.movies.ui.MovieDetails.MovieDetails;
import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.database.QueriedMovieList;
import com.udacity.oliverh.movies.utilities.MoshiAdapters.DateAdapter;
import com.udacity.oliverh.movies.utilities.MovieServiceAPI;
import com.udacity.oliverh.movies.utilities.Recycler.GridItemDecoration;
import com.udacity.oliverh.movies.utilities.Recycler.MovieAdapter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.GridItemClickListener {
    private static final int GRID_STATE_KEY = R.string.GRID_LAYOUT_STATE_PARCELABLE_KEY;
    private static final int NUM_MOVIE_GRID_SPAN_COUNT = 2;
    private MovieAdapter mAdapter;
    private RecyclerView mMovieGrid;
    private ProgressBar mProgressBar;
    private TextView mErrorMessage;
    private Parcelable mState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGrid =  findViewById(R.id.rv_movies);
        mProgressBar =  findViewById(R.id.pb_loading_indicator);
        mErrorMessage =  findViewById(R.id.tv_error_message);

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String gridStateParcelableKey = getString(GRID_STATE_KEY);

        mState = mMovieGrid.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(gridStateParcelableKey, mState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            String gridStateParcelableKey = getString(GRID_STATE_KEY);
            mState = savedInstanceState.getParcelable(gridStateParcelableKey);
        }
    }

    private void restorePosition() {
        if (mState != null) {
            mMovieGrid.getLayoutManager().onRestoreInstanceState(mState);
            mState = null;
        }
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

        onNetworkRequest();
        MovieServiceAPI.getTopRatedMovies(this, topRatedMoviesRequestCb);
    }

    private void showPopularMovies() {
        Callback popularMoviesRequestCb = getNetworkRequestCallback();

        onNetworkRequest();
        MovieServiceAPI.getPopularMovies(this, popularMoviesRequestCb);
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

        return jsonAdapter.fromJson(jsonResponse);
    }

    private Callback getNetworkRequestCallback() {
        return new Callback() {
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
                        restorePosition();
                    }
                });
            }
        };
    }
}