package com.udacity.oliverh.movies;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.oliverh.movies.databinding.MovieGridItemBinding;
import com.udacity.oliverh.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.oliverh.movies.BR.movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> mMovies = new ArrayList<Movie>();
    final private GridItemClickListener mOnClickListener;

    public interface GridItemClickListener {
        void onGridItemClick(Movie clickedMovie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private final MovieGridItemBinding movieGridItemBinding;

        public MovieViewHolder(MovieGridItemBinding binding) {
            super(binding.getRoot());

            this.movieGridItemBinding = binding;
            this.movieGridItemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie clickedMovie = mMovies.get(getAdapterPosition());
            mOnClickListener.onGridItemClick(clickedMovie);
        }

        void bind (Movie mMovie) {
            movieGridItemBinding.setVariable(movie, mMovie);
            movieGridItemBinding.executePendingBindings();
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .into(view);
    }

    public MovieAdapter( GridItemClickListener listener ) {
        this.mOnClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIDForGridItem = R.layout.movie_grid_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        MovieGridItemBinding binding = DataBindingUtil.inflate(
                inflater,
                layoutIDForGridItem,
                viewGroup,
                shouldAttachToParentImmediately);


        MovieViewHolder viewHolder = new MovieViewHolder(binding);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        if ( mMovies.size() > 0 ) {
            Movie selectedMovie = mMovies.get(i);
            movieViewHolder.bind(selectedMovie);
        }
    }

    @Override
    public int getItemCount() {
        return this.mMovies.size();
    }


    public void setMovieListData ( List<Movie> movies ) {
        this.mMovies = movies;
    }

}
