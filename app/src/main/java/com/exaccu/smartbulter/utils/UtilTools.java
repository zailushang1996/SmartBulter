package com.exaccu.smartbulter.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Author:liuzhixiang
 * PackageName:com.exaccu.smartbulter.utils
 * Create by 17864 on 2018/8/18
 *
 * @ Description:工具类
 */
public class UtilTools {
    /**
     * 设置字体
     * @param context 上下文对象
     * @param textView textView对象
     */
    public static void setFont(Context context, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
