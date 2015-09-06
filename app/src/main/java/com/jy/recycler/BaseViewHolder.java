package com.jy.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stkj.xtools.Binder;

/**
 * Created by jarrah on 2015/8/28.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
        Binder.inject(this, itemView);
    }
}
