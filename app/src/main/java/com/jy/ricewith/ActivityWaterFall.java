package com.jy.ricewith;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.jy.bean.Image;
import com.jy.framework.BaseActivity;
import com.jy.recycler.ImageViewHolder;
import com.jy.recycler.MyDecoration;
import com.stkj.recyclerviewlibary.RecyclerAdapter;
import com.stkj.recyclerviewlibary.RecyclerLoadMoreListener;
import com.stkj.recyclerviewlibary.SwipeRecyclerView;
import com.stkj.xtools.Bind;
import com.stkj.xtools.Binder;
import com.stkj.xtools.UniversalImageHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jarrah on 2015/8/28.
 */
public class ActivityWaterFall extends BaseActivity {

    @Bind(id = R.id.recyclerView)
    private SwipeRecyclerView mSwipeRecyclerView;

    private Adapter mAdapter;

    @Override
    public int onLoadViewResource() {
        return R.layout.activity_water_fall;
    }

    @Override
    public void onViewDidLoad(Bundle savedInstanceState) {
        Binder.inject(this, this);
        mAdapter = new Adapter(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mSwipeRecyclerView.getRecyclerView().setLayoutManager(layoutManager);
        mSwipeRecyclerView.setAdapter(mAdapter);
        mSwipeRecyclerView.getRecyclerView().addItemDecoration(new MyDecoration());
        mAdapter.addAll(getImages());

        mSwipeRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.addAll(getImages());
                mSwipeRecyclerView.setRefreshing(false);
            }
        });

        mSwipeRecyclerView.getRecyclerView().addOnScrollListener(new RecyclerLoadMoreListener(new RecyclerLoadMoreListener.StaggeredGridLoadMoreImpl(layoutManager)) {
            @Override
            public void onLoadMore(int current_page) {
                mSwipeRecyclerView.setRefreshing(true);
            }
        });
    }

    private Collection<Image> getImages() {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            Image image = new Image();
            image.res = R.drawable.d0 + i;
            list.add(image);
        }
        return list;
    }

    private class Adapter extends RecyclerAdapter<com.jy.bean.Image, RecyclerView.ViewHolder> {

        public Adapter(Context context) {
            super(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ImageViewHolder.newInstance(mContext);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            UniversalImageHelper.displayDrawable(imageViewHolder.imageView, getItem(position).res);
        }
    }

}
