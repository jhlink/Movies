package com.udacity.oliverh.movies.ui.MainActivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.udacity.oliverh.movies.data.network.RepositoryResponse;
import com.udacity.oliverh.movies.ui.MovieDetails.MovieDetailView;
import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.ui.MainActivity.Recycler.GridItemDecoration;
import com.udacity.oliverh.movies.ui.MainActivity.Recycler.MovieAdapter;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.GridItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GRID_STATE_KEY = R.string.GRID_LAYOUT_STATE_PARCELABLE_KEY;
    private static final int NUM_MOVIE_GRID_SPAN_COUNT = 2;
    private MovieAdapter mAdapter;
    private RecyclerView mMovieGrid;
    private ProgressBar mProgressBar;
    private TextView mErrorMessage;
    private Parcelable mState;
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeStetho();

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

        setupViewModel();
    }

    private void initializeStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void setupViewModel() {
        Log.d(TAG, "Setup View Model for Main Activity");
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.getData().observe(this, new Observer<RepositoryResponse>() {
            @Override
            public void onChanged(@Nullable RepositoryResponse response) {
                if (response == null) {
                    onNetworkFailure();
                    Log.d(TAG, "Response: Network Failure");
                    return;
                }

                if (response.getError() == null) {
                    onNetworkSuccess();
                    Log.d(TAG, "Success: Set adapter with MovieListData");
                    mAdapter.setMovieListData(response.getMovieList());
                    mAdapter.notifyDataSetChanged();
                    restorePosition();
                } else {
                    Throwable e = response.getError();
                    Log.d(TAG, "Response: Server Error | " + e.getMessage());
                }
            }
        });
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
        Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetailView.class);
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

        onNetworkRequest();
        switch (selectedMenuItem) {
            case R.id.action_popular:
                mainActivityViewModel.fetchPopularMovies(getApplicationContext());
                return true;

            case R.id.action_top_rated:
                mainActivityViewModel.fetchTopRatedMovies(getApplicationContext());
                return true;

            case R.id.action_favorites:
                mainActivityViewModel.fetchFavoriteMovies();
                return true;

            default:
                mainActivityViewModel.fetchPopularMovies(getApplicationContext());
        }

        return super.onOptionsItemSelected(item);
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
}