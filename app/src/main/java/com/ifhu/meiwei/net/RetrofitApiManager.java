package com.ifhu.meiwei.net;

import com.ifhu.meiwei.BuildConfig;

/**
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class RetrofitApiManager {

    /**
     * 正式环境
     */
    private static String RELEASE_URL = "http://正式url";
    /**
     * 预发布环境
     */
    private static String TEST_URL = "http://测试url";
    /**
     * 开发环境
     */
    private static String DEV_URL = "http://开发url";

    private static final String STRING_API_ENV = BuildConfig.STRING_API_ENV;

    private static String BASE_URL = STRING_API_ENV.equals("0")
            ? RELEASE_URL : (STRING_API_ENV.equals("1") ? TEST_URL : DEV_URL);

}
