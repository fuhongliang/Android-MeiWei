package com.ifhu.meiwei;

import android.app.Application;

import com.ifhu.meiwei.utils.AppInfo;
import com.ifhu.meiwei.utils.SharedPreUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * APP单例实例
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class MyApplication extends Application {
    public static MyApplication instance;

    public static MyApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppInfo.init(getApplicationContext());
        instance = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        SharedPreUtil.getInstance();
    }

}
