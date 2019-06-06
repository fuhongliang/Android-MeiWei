package com.ifhu.meiwei.bean;

import java.util.List;

/**
 * Created by KevinFu on 2019-06-05.
 * Copyright (c) 2019 KevinFu
 */
public class PostReviewsForm {
    String order_id;
    String member_id;
    String store_id;
    String content;
    String kouwei;
    String baozhuang;
    List<String> images;
    List<String> zan_goods_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKouwei() {
        return kouwei;
    }

    public void setKouwei(String kouwei) {
        this.kouwei = kouwei;
    }

    public String getBaozhuang() {
        return baozhuang;
    }

    public void setBaozhuang(String baozhuang) {
        this.baozhuang = baozhuang;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getZan_goods_id() {
        return zan_goods_id;
    }

    public void setZan_goods_id(List<String> zan_goods_id) {
        this.zan_goods_id = zan_goods_id;
    }
}
