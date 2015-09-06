package com.jy.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jarrah on 2015/8/28.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onLoadViewResource());
        onViewDidLoad(savedInstanceState);
    }

    public abstract int onLoadViewResource();

    public abstract void onViewDidLoad(Bundle savedInstanceState);
}
