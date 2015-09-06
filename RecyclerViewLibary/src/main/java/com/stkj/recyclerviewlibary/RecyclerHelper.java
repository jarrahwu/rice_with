package com.stkj.recyclerviewlibary;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by jarrah on 2015/8/3.
 */
public class RecyclerHelper {
    public static LinearLayoutManager createLayoutManager(int orientation, Context context, boolean reverse) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, orientation, reverse);
        return linearLayoutManager;
    }

    public static LinearLayoutManager getVerticalLayout(Context context) {
        return createLayoutManager(LinearLayoutManager.VERTICAL, context, false);
    }

    public static LinearLayoutManager getHorizontalLayout(Context context) {
        return createLayoutManager(LinearLayoutManager.HORIZONTAL, context, false);
    }
}
