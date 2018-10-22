package com.udacity.oliverh.movies.ui.MovieDetails.Recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.network.MovieVideo;
import com.udacity.oliverh.movies.databinding.VideoListItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.oliverh.movies.BR.movieVideo;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MovieVideoViewHolder> {
    private List<MovieVideo> movieVideos = new ArrayList<>();

    class MovieVideoViewHolder extends RecyclerView.ViewHolder {

        private final VideoListItemBinding videoListItemBinding;

        public MovieVideoViewHolder(VideoListItemBinding binding) {
            super(binding.getRoot());

            this.videoListItemBinding = binding;
        }

        void bind(MovieVideo iVideo) {
            videoListItemBinding.setVariable(movieVideo, iVideo);
            videoListItemBinding.executePendingBindings();
        }
    }

    @Override
    public MovieVideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIDForListItem = R.layout.video_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        VideoListItemBinding binding = DataBindingUtil.inflate(
                inflater,
                layoutIDForListItem,
                viewGroup,
                shouldAttachToParentImmediately);


        return new MovieVideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieVideoViewHolder reviewViewHolder, int i) {
        if ( movieVideos.size() > 0 ) {
            MovieVideo selectedReview = movieVideos.get(i);
            reviewViewHolder.bind(selectedReview);
        }
    }

    @Override
    public int getItemCount() {
        return this.movieVideos.size();
    }

    public void setReviewListData ( List<MovieVideo> reviews ) {
        this.movieVideos = reviews;
    }
}
