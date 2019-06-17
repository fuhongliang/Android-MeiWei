package com.ifhu.meiwei;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.ifhu.meiwei.utils.AppInfo;
import com.ifhu.meiwei.utils.SharedPreUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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
        initLogger();
        SharedPreUtil.getInstance();
        //在这里初始化
        Bugtags.start("38f0770549185acf5becd95baf2459b5", this, Bugtags.BTGInvocationEventBubble);
    }

    public void initLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(false)
                // (Optional) How many method line to show. Default 2
                .methodCount(0)
                // (Optional) Hides internal method calls up to offset. Default 5
                .methodOffset(7)
                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("MeiWei")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
