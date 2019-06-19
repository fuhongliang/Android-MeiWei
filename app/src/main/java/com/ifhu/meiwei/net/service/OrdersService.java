package com.ifhu.meiwei.net.service;

import com.ifhu.meiwei.bean.BaseEntity;
import com.ifhu.meiwei.bean.ComformOrderBean;
import com.ifhu.meiwei.bean.OrderBean;
import com.ifhu.meiwei.bean.OrderinfoBean;
import com.ifhu.meiwei.bean.PostOrderForm;
import com.ifhu.meiwei.bean.PostReviewsForm;
import com.ifhu.meiwei.bean.WXPayBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 订单相关的服务
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public interface OrdersService {
    @FormUrlEncoded
    @POST("order_list")
    public Observable<BaseEntity<List<OrderBean>>> orderList(@Field("member_id") int member_id, @Field("type") int type);

    @FormUrlEncoded
    @POST("order_info")
    public Observable<BaseEntity<OrderinfoBean>> orderInfo(@Field("order_id") int order_id);

    @POST("get_order_state")
    public Observable<BaseEntity<Object>> orderState(@Field("member_id") int member_id,@Field("order_id") int order_id);

    @FormUrlEncoded
    @POST("go_settlement")
    public Observable<BaseEntity<ComformOrderBean>> goSettlement(@Field("member_id") String member_id, @Field("store_id") String store_id);

    @POST("buy_step")
    public Observable<BaseEntity<WXPayBean>> buyStep(@Body PostOrderForm postOrderForm);

    @POST("store_com")
    public Observable<BaseEntity<Object>> storeCom(@Body PostReviewsForm reviewsForm);

}
