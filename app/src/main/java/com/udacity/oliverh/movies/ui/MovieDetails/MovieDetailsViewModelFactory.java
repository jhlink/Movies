package com.udacity.oliverh.movies.ui.MovieDetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Context mContext;
    private final int mMovieId;

    public MovieDetailsViewModelFactory(Context context, int movieId) {
        mContext = context;
        mMovieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mContext, mMovieId);
    }
}
