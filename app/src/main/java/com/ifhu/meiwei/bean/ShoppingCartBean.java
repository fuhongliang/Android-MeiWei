package com.ifhu.meiwei.bean;

import java.util.List;

public class ShoppingCartBean {

    /**
     * list : [{"goods_id":5,"goods_name":"","goods_price":"500.00","goods_num":4,"goods_image":"7JB0C83ESNuwbnXAnttvb3KDFQVA3rbHO6KyoDPb.jpeg"}]
     * amount : 2000.00
     * store : {"store_name":"店铺1","store_id":3}
     */

    private String amount;
    private StoreBean store;
    private List<ListBean> list;
    boolean isExpend = false;

    public boolean isExpend() {
        return isExpend;
    }


    public void setExpend(boolean expend) {
        isExpend = expend;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class StoreBean {
        /**
         * store_name : 店铺1
         * store_id : 3
         */

        private String store_name;
        private int store_id;

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }
    }

    public static class ListBean {
        /**
         * goods_id : 5
         * goods_name :
         * goods_price : 500.00
         * goods_num : 4
         * goods_image : 7JB0C83ESNuwbnXAnttvb3KDFQVA3rbHO6KyoDPb.jpeg
         */

        private int goods_id;
        private String goods_name;
        private String goods_price;
        private int goods_num;
        private String goods_image;



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

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }
    }
}
