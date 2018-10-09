package com.udacity.oliverh.movies.ui.MainActivity.Recycler;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    public GridItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {

        outRect.left = mSpace;
        outRect.right = 0;
        outRect.bottom = mSpace;

        if (parent.getChildLayoutPosition(view) == 0
                || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = mSpace;
        } else {
            outRect.top = 0;
        }
    }
}
