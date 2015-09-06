package com.stkj.recyclerviewlibary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by jarrah on 2015/8/7.
 */
public abstract class MultiTypeAdapter<E extends MultiTypeAdapter.TypeMapping, VH extends RecyclerView.ViewHolder> extends RecyclerAdapter<E, VH> {


    public MultiTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getTypeId();
    }

    public interface TypeMapping {
        int getTypeId();
    }


}
