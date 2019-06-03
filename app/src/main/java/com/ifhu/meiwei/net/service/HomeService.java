package com.ifhu.meiwei.net.service;

import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.EvaluationBean;
import com.ifhu.meiwei.bean.HomeBean;
import com.ifhu.meiwei.bean.MenuBean;

import java.util.List;

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

    @FormUrlEncoded
    @POST("store_info")
    public Observable<BaseEntity<MenuBean>> storeInfo(@Field("store_id") String store_id, @Field("member_id") String member_id, @Field("class_id") String class_id, @Field("tab_id") int tab_id, @Field("type") int type);

    @FormUrlEncoded
    @POST("get_voucher")
    public Observable<BaseEntity<Object>> getVoucher(@Field("voucher_t_id") int voucher_t_id, @Field("member_id") int member_id);

    @FormUrlEncoded
    @POST("store_com")
    public Observable<BaseEntity<EvaluationBean>> storeCom(@Field("order_id") String order_id, @Field("member_id") String member_id);


}
