package com.jy.ricewith;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

/**
 * Created by jarrah on 2015/8/24.
 */
public class ToolbarBuilder {

    public static Toolbar build(AppCompatActivity activity, Toolbar toolbar) {
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(android.R.id.content);
        viewGroup.addView(toolbar, new ViewGroup.LayoutParams(-1, -2));
        activity.setSupportActionBar(toolbar);
        return toolbar;
    }

    public static Toolbar build(AppCompatActivity activity, int toolbarLayout) {
        Toolbar toolbar = (Toolbar) activity.getLayoutInflater().inflate(toolbarLayout, null, false);
        return build(activity, toolbar);
    }
}
