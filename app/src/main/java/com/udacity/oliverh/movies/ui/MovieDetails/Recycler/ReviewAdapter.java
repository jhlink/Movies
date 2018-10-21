package com.udacity.oliverh.movies.ui.ReviewDetails.Recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.udacity.oliverh.movies.R;
import com.udacity.oliverh.movies.data.network.Review;
import com.udacity.oliverh.movies.databinding.ReviewListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> mReviews = new ArrayList<>();

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ReviewListItemBinding reviewListItemBinding;

        public ReviewViewHolder(ReviewListItemBinding binding) {
            super(binding.getRoot());

            this.reviewListItemBinding = binding;
        }

        void bind(Review iReview) {
            reviewListItemBinding.setVariable(review, iReview);
            reviewListItemBinding.executePendingBindings();
        }
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutIDForListItem = R.layout.review_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        ReviewListItemBinding binding = DataBindingUtil.inflate(
                inflater,
                layoutIDForListItem,
                viewGroup,
                shouldAttachToParentImmediately);


        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder reviewViewHolder, int i) {
        if ( mReviews.size() > 0 ) {
            Review selectedReview = mReviews.get(i);
            reviewViewHolder.bind(selectedReview);
        }
    }

    @Override
    public int getItemCount() {
        return this.mReviews.size();
    }


    public void setReviewListData ( List<Review> reviews ) {
        this.mReviews = reviews;
    }
}
