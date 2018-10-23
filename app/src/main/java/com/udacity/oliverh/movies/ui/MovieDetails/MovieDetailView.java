package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.database.Movie;
import com.udacity.oliverh.movies.data.network.RepositoryResponse;
import com.udacity.oliverh.movies.databinding.MovieDetailsBinding;
import com.udacity.oliverh.movies.ui.MovieDetails.Recycler.ReviewAdapter;
import com.udacity.oliverh.movies.ui.MovieDetails.Recycler.VideoAdapter;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieDetailView extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final static String TAG = MovieDetailView.class.getSimpleName();
    private Context mContext;
    private MovieDetailsViewModel movieDetailsViewModel;
    private MovieDetailsBinding binding;
    private Movie movieData;

    // Recycler view for Movie Reviews
    private ReviewAdapter reviewAdapter;
    private RecyclerView reviewList;

    // Recycler view for Movie Videos
    private VideoAdapter videoAdapter;
    private RecyclerView videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.movie_details);
        mContext = getApplicationContext();

        Intent activityInitiatingIntent = getIntent();

        setupReviewRecyclerView();
        setupVideoRecyclerView();

        String parcelTag = getString(R.string.ParcelID);
        if (activityInitiatingIntent.hasExtra(parcelTag)) {
            movieData = activityInitiatingIntent.getParcelableExtra(parcelTag);

            setupViewModel(movieData.getId());

            binding.setVariable(movie, movieData);
            binding.favoriteBtn.setOnCheckedChangeListener(this);
            binding.executePendingBindings();
        }
    }

    private void setupReviewRecyclerView() {
        reviewList = binding.rvReviews;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        reviewList.setLayoutManager(layoutManager);
        reviewList.setNestedScrollingEnabled(false);
        reviewAdapter = new ReviewAdapter();
        reviewList.setAdapter(reviewAdapter);
    }

    private void setupVideoRecyclerView() {
        videoList = binding.rvVideos;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        videoList.setLayoutManager(layoutManager);
        videoList.setNestedScrollingEnabled(false);
        videoAdapter = new VideoAdapter();
        videoList.setAdapter(videoAdapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Log.d(TAG, "Button checked.");
            insertMovie(movieData);
        } else {
            Log.d(TAG, "Button unchecked.");
            deleteMovie(movieData);
        }
    }

    private void deleteMovie(Movie iMovie) {
        movieDetailsViewModel.deleteMovie(iMovie);
    }

    private void insertMovie(Movie iMovie) {
        movieDetailsViewModel.insertMovie(iMovie);
    }

    private void setupViewModel(int movieId) {
        MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(mContext, movieId);
        movieDetailsViewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.getMovie().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                boolean isFavorited = movie != null;
                binding.favoriteBtn.setChecked(isFavorited);
                movieDetailsViewModel.getMovie().removeObserver(this);
            }
        });

        movieDetailsViewModel.getReviews().observe(this, new Observer<RepositoryResponse>() {
            @Override
            public void onChanged(@Nullable RepositoryResponse response) {
                if (response == null) {
                    Log.d(TAG, "Response: Network Failure");
                    return;
                }

                if (response.getError() == null) {
                    Log.d(TAG, "Success: Set adapter with MovieReviews");
                    reviewAdapter.setReviewListData(response.getListOfData());
                    reviewAdapter.notifyDataSetChanged();
                } else {
                    Throwable e = response.getError();
                    Log.d(TAG, "Response: Server Error | " + e.getMessage());
                }
            }
        });

        movieDetailsViewModel.getVideos().observe(this, new Observer<RepositoryResponse>() {
            @Override
            public void onChanged(@Nullable RepositoryResponse response) {
                if (response == null) {
                    Log.d(TAG, "Response: Network Failure");
                    return;
                }

                if (response.getError() == null) {
                    Log.d(TAG, "Success: Set adapter with MovieVideos");
                    videoAdapter.setMovieVideosListData(response.getListOfData());
                    videoAdapter.notifyDataSetChanged();
                } else {
                    Throwable e = response.getError();
                    Log.d(TAG, "Response: Server Error | " + e.getMessage());
                }
            }
        });

    }
}

