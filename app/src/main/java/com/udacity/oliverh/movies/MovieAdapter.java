package com.udacity.oliverh.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberOfMovies;

    public MovieAdapter(int numOfMovies) {
        mNumberOfMovies = numOfMovies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIDForGridItem = R.layout.movie_grid_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIDForGridItem, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mNumberOfMovies;
    }
}
