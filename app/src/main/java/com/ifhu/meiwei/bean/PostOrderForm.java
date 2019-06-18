package com.ifhu.meiwei.bean;

/**
 * Created by KevinFu on 2019-06-05.
 * Copyright (c) 2019 KevinFu
 */
public class PostOrderForm {
    String member_id;
    String store_id;
    String address_id;
    String voucher_id;
    String payment_code;
    String shipping_time;
    String order_message;
    String peisong_amount;

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

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getOrder_message() {
        return order_message;
    }

    public void setOrder_message(String order_message) {
        this.order_message = order_message;
    }

    public String getPeisong_amount() {
        return peisong_amount;
    }

    public void setPeisong_amount(String peisong_amount) {
        this.peisong_amount = peisong_amount;
    }
}
