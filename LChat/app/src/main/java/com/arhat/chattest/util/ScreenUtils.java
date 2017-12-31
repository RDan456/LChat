package com.arhat.chattest.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * ScreenUtils
 * Created by hanj on 14-9-25.
 */
public class ScreenUtils {
    private static int screenW;
    private static int screenH;
    private static float screenDensity;

    public static void initScreen(Activity mActivity){
        DisplayMetrics metric = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenW = metric.widthPixels;
        screenH = metric.heightPixels;
        screenDensity = metric.density;
    }

    public static int getScreenW(){
        return screenW;
    }

    public static int getScreenH(){
        return screenH;
    }

    public static float getScreenDensity(){
        return screenDensity;
    }

    public static int dp2px(float dpValue) {
        return (int) (dpValue * getScreenDensity() + 0.5f);
    }

    public static int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }
}
