package com.ifhu.meiwei.net;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ifhu.meiwei.BuildConfig;
import com.ifhu.meiwei.utils.UserLogic;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class RetrofitApiManager {
    private static final long TIMEOUT = 3000;
    private static Retrofit retrofit;
    private static Retrofit uploadRetrofit;

    /**
     * 正式环境
     */
    private static String RELEASE_URL = "http://47.111.27.189:88/u1/";
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

    private RetrofitApiManager() {
    }

    /**
     * 获取Retrofit实例
     *
     * @return Retrofit
     */
    public static Retrofit getClientApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericClient())
                    .build();
        }
        return retrofit;
    }

    /**
     * 获取OkHttpClient
     *
     * @return OkHttpClient
     */
    private static OkHttpClient genericClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(chain -> {
                    String token = "";
                    if (UserLogic.isLogin()) {
                        token = UserLogic.getUser().getToken();
                    }
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .addHeader("token", token)
                            .build();
                    return chain.proceed(request);
                })
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> Logger.d(message)).setLevel(HttpLoggingInterceptor.Level.BODY));

        OkHttpClient httpClient = builder.build();
        return httpClient;
    }


    /**
     * 上传专用
     *
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T createUpload(final Class<T> service) {
        return getUploadClientApi().create(service);
    }

    /**
     * 上传图片专用的Retrofit
     */
    public static Retrofit getUploadClientApi() {
        if (uploadRetrofit == null) {
            uploadRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(genericUploadClient())
                    .build();
        }
        return uploadRetrofit;
    }

    private static OkHttpClient genericUploadClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String token = "";
                    if (UserLogic.isLogin()) {
                        token = UserLogic.getUser().getToken();
                    }
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("token", token)
                            .build();
                    return chain.proceed(request);
                })
                .addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> Log.d("RetrofitAPIManager", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return httpClient;
    }
}
