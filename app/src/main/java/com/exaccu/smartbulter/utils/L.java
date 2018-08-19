package com.exaccu.smartbulter.utils;

import android.util.Log;

/**
 * Author:liuzhixiang
 * PackageName:com.exaccu.smartbulter.utils
 * Create by 17864 on 2018/8/19
 *
 * @ Description:
 */
public class L {

    //开关
    public static final boolean DEBUG = true;

    //TAG
    public static final String TAG = "SmartBulter";

    //四个日志等级
    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
