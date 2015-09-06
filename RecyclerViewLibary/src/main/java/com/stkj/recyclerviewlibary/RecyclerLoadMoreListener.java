package com.stkj.recyclerviewlibary;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class RecyclerLoadMoreListener extends RecyclerView.OnScrollListener {
    public static String TAG = RecyclerLoadMoreListener.class.getSimpleName();

    private int previousTotal = 0;
    private boolean loading = true;
    private int invisibleThreshold = 5;
    int previousCount, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private ILoadMore loadMoreImpl;

    public RecyclerLoadMoreListener(ILoadMore loadMoreImpl) {
        this.loadMoreImpl = loadMoreImpl;
    }

    public void reset() {
        previousTotal = 0;
        loading = true;
        invisibleThreshold = 2;
        previousCount = visibleItemCount = totalItemCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = loadMoreImpl.getTotalItemCount();
        previousCount = loadMoreImpl.getPreviousItemCount();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (previousCount + invisibleThreshold) && dy >=0) {

            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page);

    public static class LinearLoadMoreImpl implements ILoadMore{

        private final LinearLayoutManager mLinearLayoutManager;

        public LinearLoadMoreImpl(LinearLayoutManager linearLayoutManager) {
            mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public int getTotalItemCount() {
            return mLinearLayoutManager.getItemCount();
        }

        @Override
        public int getPreviousItemCount() {
            return mLinearLayoutManager.findFirstVisibleItemPosition();
        }
    }

    public static class GridLoadMoreImpl implements ILoadMore {

        private final GridLayoutManager mGridLayoutManager;

        public GridLoadMoreImpl(GridLayoutManager gridLayoutManager) {
            mGridLayoutManager = gridLayoutManager;
        }

        @Override
        public int getTotalItemCount() {
            return mGridLayoutManager.getItemCount();
        }

        @Override
        public int getPreviousItemCount() {
            return mGridLayoutManager.findFirstVisibleItemPosition();
        }
    }

    public static class StaggeredGridLoadMoreImpl implements ILoadMore {

        private final StaggeredGridLayoutManager mStaggeredGridLayoutManager;

        public StaggeredGridLoadMoreImpl(StaggeredGridLayoutManager staggeredGridLayoutManager) {
            mStaggeredGridLayoutManager = staggeredGridLayoutManager;
        }

        @Override
        public int getTotalItemCount() {
            return mStaggeredGridLayoutManager.getItemCount();
        }

        @Override
        public int getPreviousItemCount() {
            int[] items = mStaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
            return items[items.length - 1];
        }
    }
}