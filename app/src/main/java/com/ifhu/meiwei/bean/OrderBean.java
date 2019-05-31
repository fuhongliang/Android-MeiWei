package com.ifhu.meiwei.bean;

import java.util.List;

public class OrderBean {

    /**
     * order_id : 15
     * store_name : 卓诗尼女装店
     * store_avatar : xMEsiuRz5c3ygcXv4UvrfIOBqoLwPwTRdWYaN73B.webp
     * order_state : 25
     * goods_list : [{"goods_id":47,"goods_name":"小皮鞋","goods_price":"500.00","goods_num":1},{"goods_id":48,"goods_name":"时尚运动鞋","goods_price":"250.00","goods_num":1},{"goods_id":49,"goods_name":"黑色小皮鞋","goods_price":"600.00","goods_num":1}]
     * total_amount : 1350
     */

    private int order_id;
    private String store_name;
    private String store_avatar;
    private int order_state;
    private int total_amount;
    private List<GoodsListBean> goods_list;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_avatar() {
        return store_avatar;
    }

    public void setStore_avatar(String store_avatar) {
        this.store_avatar = store_avatar;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_id : 47
         * goods_name : 小皮鞋
         * goods_price : 500.00
         * goods_num : 1
         */

        private int goods_id;
        private String goods_name;
        private String goods_price;
        private int goods_num;

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

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }
    }
}
