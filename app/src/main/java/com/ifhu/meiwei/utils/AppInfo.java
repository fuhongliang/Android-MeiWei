package com.ifhu.meiwei.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * 获取APP基本信息，手机宽高等
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class AppInfo {

    public static String dType;
    public static String dVersion;

    public static int appCode;
    public static String appVersion;

    public static int width;
    public static int height;
    public static float density;
    public static int densityDpi;

    public static void init(Context mContext) {
        dType = Build.MODEL;
        dVersion = Build.VERSION.SDK_INT + "_" + Build.VERSION.RELEASE;
        PackageInfo pi = null;
        try {
            pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != pi) {
            appVersion = pi.versionName;
            appCode = pi.versionCode;
        } else {
            appVersion = "";
            appCode = 0;
        }

        initDisplay(mContext);
    }

    public static void initDisplay(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        density = metrics.density;
        densityDpi = metrics.densityDpi;
    }
}
