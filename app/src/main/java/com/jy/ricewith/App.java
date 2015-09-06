package com.jy.ricewith;

import android.app.Application;

import com.stkj.xtools.UniversalImageHelper;

/**
 * Created by jarrah on 2015/8/28.
 */
public class App extends Application {
    public static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if(sInstance == null) {
            sInstance = this;
        }
        UniversalImageHelper.initImageLoader(this);
    }

    public static synchronized App getsInstance() {
        return sInstance;
    }
}
