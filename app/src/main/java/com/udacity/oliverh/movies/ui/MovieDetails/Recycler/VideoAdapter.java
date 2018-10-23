package com.udacity.oliverh.movies.ui.MovieDetails.Recycler;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.network.MovieVideo;
import com.udacity.oliverh.movies.databinding.VideoListItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.oliverh.movies.BR.movieVideo;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MovieVideoViewHolder> {
    private List<MovieVideo> movieVideos = new ArrayList<>();

    class MovieVideoViewHolder extends RecyclerView.ViewHolder
    implements ImageButton.OnClickListener {

        private final VideoListItemBinding videoListItemBinding;
        private final String TAG = MovieVideoViewHolder.class.getSimpleName();

        public MovieVideoViewHolder(VideoListItemBinding binding) {
            super(binding.getRoot());

            this.videoListItemBinding = binding;
        }

        void bind(MovieVideo iVideo) {
            videoListItemBinding.setVariable(movieVideo, iVideo);
            videoListItemBinding.imageButton.setOnClickListener(this);
            videoListItemBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            MovieVideo clickedMovieVideo = movieVideos.get(getAdapterPosition());
            String youtubeVideoKey = clickedMovieVideo.getKey();
            Intent launchYoutubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeVideoKey));

            Context context = v.getContext();
            if (launchYoutubeIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(launchYoutubeIntent);
                Log.d(TAG, "Intent to launch Youtube application succeeded");
            } else {
                Log.d(TAG, "Intent to launch Youtube application failed");
            }
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
    public void onBindViewHolder(MovieVideoViewHolder movieVideoViewHolder, int i) {
        if ( movieVideos.size() > 0 ) {
            MovieVideo selectedMovieVideo = movieVideos.get(i);
            movieVideoViewHolder.bind(selectedMovieVideo);
        }
    }

    @Override
    public int getItemCount() {
        return movieVideos.size();
    }

    public void setMovieVideosListData ( List<MovieVideo> videos ) {
        this.movieVideos = videos;
    }
}
