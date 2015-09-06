package com.stkj.recyclerviewlibary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * E 数据类型
 * VH ViewHolder 类型
 * Created by jarrah on 2015/8/3.
 */
public abstract class RecyclerAdapter<E, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Object mLock = new Object();
    protected final Context mContext;

    private List<E> mDataSet;

    public RecyclerAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<E>();
    }

    public boolean add(E e) {
        boolean ret;
        synchronized (mLock) {
            ret = mDataSet.add(e);
        }
        if (ret)
            notifyItemInserted(mDataSet.size() - 1);
        return ret;
    }

    public boolean remove(E e) {
        boolean ret;
        int position;
        synchronized (mLock) {
            position = mDataSet.indexOf(e);
            ret = mDataSet.remove(e);
            Log.e("e", "remove : " + ret + "position" + position + " data set size : " + mDataSet.size());
            if (ret){
                notifyItemRemoved(position);
            }

        }
        return ret;
    }

    public void insert(int position, E e) {
        synchronized (mLock) {
            mDataSet.add(position, e);
        }
        notifyItemInserted(position);
    }

    public boolean remove(int position) {
        E e = getItem(position);
        return remove(e);
    }

    public void clear() {
        synchronized (mLock) {
            mDataSet.clear();
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<E> collection) {
        synchronized (mLock) {
            mDataSet.addAll(collection);
        }
        notifyDataSetChanged();
    }

    public E getItem(int position) {
        synchronized (mLock) {
            return mDataSet.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }
}
