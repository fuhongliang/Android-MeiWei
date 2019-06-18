package com.ifhu.meiwei.bean;

import java.util.List;

/**
 * Created by KevinFu on 2019-06-05.
 * Copyright (c) 2019 KevinFu
 */
public class ComformOrderBean {

    /**
     * address : {"true_name":"11","mob_phone":"11","area_info":"四川省自贡市","address":"湍河街道办","address_id":1}
     * store_detail : {"store_id":3,"store_name":"店铺1"}
     * goods_detail : [{"cart_id":45,"goods_id":5,"goods_name":"","goods_price":"10.00","goods_image":"","goods_num":1,"goods_marketprice":"88.00"}]
     * peisong_amount : 5
     * manjian_amount : 0
     * daijinquan_amount : 300
     * daijinquan_list : [{"voucher_price":300,"voucher_id":2},{"voucher_price":200,"voucher_id":1},{"voucher_price":100,"voucher_id":3},{"voucher_price":100,"voucher_id":4}]
     * total_amount : 0
     */

    private MyAddressBean address;
    private StoreDetailBean store_detail;
    private int peisong_amount;
    private int manjian_amount;
    private int daijinquan_amount;
    private int total_amount;
    private List<GoodsDetailBean> goods_detail;
    private List<DaijinquanListBean> daijinquan_list;

    public MyAddressBean getAddress() {
        return address;
    }

    public void setAddress(MyAddressBean address) {
        this.address = address;
    }

    public StoreDetailBean getStore_detail() {
        return store_detail;
    }

    public void setStore_detail(StoreDetailBean store_detail) {
        this.store_detail = store_detail;
    }

    public int getPeisong_amount() {
        return peisong_amount;
    }

    public void setPeisong_amount(int peisong_amount) {
        this.peisong_amount = peisong_amount;
    }

    public int getManjian_amount() {
        return manjian_amount;
    }

    public void setManjian_amount(int manjian_amount) {
        this.manjian_amount = manjian_amount;
    }

    public int getDaijinquan_amount() {
        return daijinquan_amount;
    }

    public void setDaijinquan_amount(int daijinquan_amount) {
        this.daijinquan_amount = daijinquan_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public List<GoodsDetailBean> getGoods_detail() {
        return goods_detail;
    }

    public void setGoods_detail(List<GoodsDetailBean> goods_detail) {
        this.goods_detail = goods_detail;
    }

    public List<DaijinquanListBean> getDaijinquan_list() {
        return daijinquan_list;
    }

    public void setDaijinquan_list(List<DaijinquanListBean> daijinquan_list) {
        this.daijinquan_list = daijinquan_list;
    }

    public static class StoreDetailBean {
        /**
         * store_id : 3
         * store_name : 店铺1
         */

        private int store_id;
        private String store_name;

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }
    }

    public static class GoodsDetailBean {
        /**
         * cart_id : 45
         * goods_id : 5
         * goods_name :
         * goods_price : 10.00
         * goods_image :
         * goods_num : 1
         * goods_marketprice : 88.00
         */

        private int cart_id;
        private int goods_id;
        private String goods_name;
        private String goods_price;
        private String goods_image;
        private int goods_num;
        private String goods_marketprice;

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cart_id) {
            this.cart_id = cart_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }
    }

    public static class DaijinquanListBean {
        /**
         * voucher_price : 300
         * voucher_id : 2
         */

        private int voucher_price;
        private int voucher_id;

        public int getVoucher_price() {
            return voucher_price;
        }

        public void setVoucher_price(int voucher_price) {
            this.voucher_price = voucher_price;
        }

        public int getVoucher_id() {
            return voucher_id;
        }

        public void setVoucher_id(int voucher_id) {
            this.voucher_id = voucher_id;
        }
    }
}
