package com.stkj.xtools;

import android.view.View;

/**
 * Created by jarrah on 2015/8/27.
 */
public abstract class ClickHandler implements View.OnClickListener{

    @Override
    public void onClick(View v) {
        onClickImpl(v, v.getId());
    }

    protected abstract void onClickImpl(View v, int id);
}
