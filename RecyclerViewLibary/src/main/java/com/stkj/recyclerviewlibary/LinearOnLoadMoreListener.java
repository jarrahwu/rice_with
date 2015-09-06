package com.stkj.recyclerviewlibary;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class LinearOnLoadMoreListener extends RecyclerView.OnScrollListener {
    public static String TAG = LinearOnLoadMoreListener.class.getSimpleName();

    private int previousTotal = 0;
    private boolean loading = true;
    private int invisibleThreshold = 5;
    int previousCount, visibleItemCount, totalItemCount;

    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public LinearOnLoadMoreListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
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
        totalItemCount = mLinearLayoutManager.getItemCount();
        previousCount = mLinearLayoutManager.findFirstVisibleItemPosition();

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
}