package com.jy.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jy.ricewith.R;
import com.stkj.xtools.Bind;

/**
 * Created by jarrah on 2015/8/28.
 */
public class ImageViewHolder extends BaseViewHolder {

    @Bind(id = R.id.img)
    public ImageView imageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
    }

    public static ImageViewHolder newInstance(Context context) {
        LayoutInflater lf = LayoutInflater.from(context);
        View v = lf.inflate(R.layout.image_view_holder, null, false);
        return new ImageViewHolder(v);
    }
}
