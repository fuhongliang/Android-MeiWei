package com.ifhu.meiwei.utils;

/**
 * Created by KevinFu on 2019-06-06.
 * Copyright (c) 2019 KevinFu
 */

import android.content.Context;

/**
 * 用于解决provider冲突的util
 */
public class ProviderUtil {
    public static String getFileProviderName(Context context){
        return context.getPackageName()+".provider";
    }
}
