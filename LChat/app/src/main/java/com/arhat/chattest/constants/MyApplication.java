package com.arhat.chattest.constants;

import android.app.Application;

/**
 * Created by Arhat on 2016/9/9.
 */
public class MyApplication extends Application {

    private static MyApplication instance = null;

    public static MyApplication getInstance() {

        return instance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
    }
}
