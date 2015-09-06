package com.stkj.recyclerviewlibary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by jarrah on 2015/8/3.
 */
public class SimpleViewHolder extends RecyclerView.ViewHolder{

    public SimpleViewHolder(View itemView) {
        super(itemView);
    }

    public View findViewById(int resId) {
        return itemView.findViewById(resId);
    }

    public static SimpleViewHolder from(Context context, int res) {
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = lf.inflate(res, null, false);
        return new SimpleViewHolder(itemView);
    }
}
