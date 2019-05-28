package com.ifhu.meiwei.net.service;

import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 首页模块相关的服务
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public interface HomeService {
    @FormUrlEncoded
    @POST("home_page")
    public Observable<BaseEntity<HomeBean>> keyword(@Field("keyword") String keyword, @Field("page") int page, @Field("longitude") String longitude, @Field("dimension") String dimension, @Field("type") String type);

}
