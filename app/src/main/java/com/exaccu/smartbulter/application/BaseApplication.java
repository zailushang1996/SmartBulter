package com.exaccu.smartbulter.application;

import android.app.Application;

import com.exaccu.smartbulter.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Author:liuzhixiang
 * Create by 17864 on 2018/8/17
 *
 * @ Description:
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_KEY);
    }


}
