package com.exaccu.smartbulter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author:liuzhixiang
 * PackageName:com.exaccu.smartbulter.utils
 * Create by 17864 on 2018/8/19
 *
 * @ Description:sharePreference工具类
 */
public class ShareUtils {

    public static final String NAME = "config";

    /**
     * SharePreference存放数据
     * @param mContext 上下文对象
     * @param key 键
     * @param value 值
     */
    public static void putString(Context mContext, String key, String value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * SharePreference获取数据
     * @param mContext 上下文对象
     * @param key 键
     * @param defValue 默认值
     * @return 获取到的字符串
     */
    public static String getString(Context mContext, String key, String defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * SharePreference存放数据
     * @param mContext 上下文对象
     * @param key 键
     * @param value 值
     */
    public static void putInt(Context mContext, String key, int value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * SharePreference获取数据
     * @param mContext 上下文对象
     * @param key 键
     * @param defValue 默认值
     * @return 获取到的数值
     */
    public static int getInt(Context mContext, String key, int defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * SharePreference存放数据
     * @param mContext 上下文对象
     * @param key 键
     * @param value 值
     */
    public static void putBoolean(Context mContext, String key, boolean value) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * SharePreference获取数据
     * @param mContext 上下文对象
     * @param key 键
     * @param defValue 默认值
     * @return 获取到的Boolean值
     */
    public static boolean getBoolean(Context mContext, String key, boolean defValue) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 删除单个 sharePreference
     * @param mContext 上下文对象
     * @param key 键
     */
    public static void deleShare(Context mContext, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    /**
     * 删除全部sharePreference
     * @param context 上下文对象
     */
    public static void deleAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
