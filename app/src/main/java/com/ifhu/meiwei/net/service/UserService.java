package com.ifhu.meiwei.net.service;

import com.ifhu.meiwei.bean.BaseEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 用户相关的服务
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public interface UserService {
    @FormUrlEncoded
    @POST("sms_login")
    public Observable<BaseEntity<Object>> smsLogin(@Field("phone_number") String phone_number, @Field("code") String code, @Field("device_tokens") String device_tokens, @Field("app_type") String app_type);

    @FormUrlEncoded
    @POST("get_sms")
    public Observable<BaseEntity<Object>> getSms(@Field("phone_number") String phone_number);

}
