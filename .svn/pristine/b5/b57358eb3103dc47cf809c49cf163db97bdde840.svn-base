package com.arhat.chattest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 判断网络是否可用
 * Created by Arhat on 2016/10/16.
 */

public class Network {

    public static boolean isNetAvailable(Context context) {

        boolean flag;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isAvailable()) {
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }
}
