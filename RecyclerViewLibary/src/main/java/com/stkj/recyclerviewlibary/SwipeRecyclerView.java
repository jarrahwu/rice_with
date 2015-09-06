package com.stkj.recyclerviewlibary;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by jarrah on 2015/8/5.
 */
public class SwipeRecyclerView extends SwipeRefreshLayout {

    public RecyclerView recyclerView;

    public SwipeListener mSwipeListener;
    private InternalLoadMoreListener mOnLoadMoreListener;

    public SwipeRecyclerView(Context context) {
        super(context);
        init();
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        recyclerView = new RecyclerView(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        addView(recyclerView);
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        mSwipeListener = swipeListener;
        setupRefresh();
        setupLoadMore();
    }

    private void setupLoadMore() {
        mOnLoadMoreListener = new InternalLoadMoreListener();
        recyclerView.addOnScrollListener(mOnLoadMoreListener);
    }

    private void setupRefresh() {
        setOnRefreshListener(new InternalRefreshListener());
    }

    public static abstract  class SwipeListener {

        public abstract void onLoadMore(int current_page);

        public abstract void onRefresh();
    }

    private class InternalRefreshListener implements OnRefreshListener {
        @Override
        public void onRefresh() {
            if (mSwipeListener != null) {
                mOnLoadMoreListener.reset();
                mSwipeListener.onRefresh();
            }
        }
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        getRecyclerView().setAdapter(adapter);
    }

    private class InternalLoadMoreListener extends LinearOnLoadMoreListener {
        public InternalLoadMoreListener() {
            super((LinearLayoutManager) SwipeRecyclerView.this.recyclerView.getLayoutManager());
        }

        @Override
        public void onLoadMore(int current_page) {
            if (mSwipeListener != null) {
                setRefreshing(true);
                mSwipeListener.onLoadMore(current_page);
            }
        }
    }
}
