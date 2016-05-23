package com.abed.propertyguru.view.misc;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Mindvalley on 3/11/16.
 */
//Item Decoration class
public class VerticalEqualSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int mSpaceHeight;

    public VerticalEqualSpaceItemDecoration(int mSpaceHeight) {
        this.mSpaceHeight = mSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = mSpaceHeight;
        outRect.top = mSpaceHeight;
        outRect.left = 0;
        outRect.right = 0;
    }

}
