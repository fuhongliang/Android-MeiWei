package com.ifhu.meiwei.bean;

import java.util.List;

/**
 * @author fuhongliang
 */
public class MerchantBean {


    /**
     * store_info : {"store_id":5,"store_name":"lijing店铺1","store_avatar":"7mnpQn1EEfPKD6TjPPnJi66AspqMLiLyi8wqKdqD.jpeg","store_sales":0,"store_credit":0,"store_description":"热滴我陪你走","daijinquan":0}
     * is_collect : false
     * manjian : [{"rule_id":1,"price":10,"discount":5}]
     * goods_list : [{"stc_id":"hot","stc_name":"热销","goods":[{"goods_id":40,"goods_name":"嗯","goods_price":"5.00","goods_marketprice":"55.00","goods_desc":"","img_name":"BekaQ0OvEA8OpJkQrAg9meaRAztXVz9bmW6kqAmF.jpeg","goods_salenum":1,"zan":0},{"goods_id":38,"goods_name":"111","goods_price":"1111.00","goods_marketprice":"111.00","goods_desc":"11","img_name":"11","goods_salenum":0,"zan":0},{"goods_id":39,"goods_name":"莫1","goods_price":"88.00","goods_marketprice":"888.00","goods_desc":"","img_name":"WYlqzgedvgUJfXVwjTU7OB7MRjAN1838aws44RNZ.jpeg","goods_salenum":0,"zan":0}]},{"stc_id":"xianshi","stc_name":"折扣","goods":[{"goods_id":40,"goods_name":"嗯","goods_price":"5.00","goods_marketprice":"55.00","goods_desc":"","img_name":"BekaQ0OvEA8OpJkQrAg9meaRAztXVz9bmW6kqAmF.jpeg","goods_salenum":1,"zan":0}]},{"stc_id":"taozhuang","stc_name":"优惠","goods":[{"goods_id":40,"goods_name":"嗯","goods_price":"5.00","goods_marketprice":"55.00","goods_desc":"","img_name":"BekaQ0OvEA8OpJkQrAg9meaRAztXVz9bmW6kqAmF.jpeg","goods_salenum":1,"zan":0}]},{"stc_id":"3","stc_name":"分类一","goods":[{"goods_id":39,"goods_name":"莫1","goods_price":"88.00","goods_marketprice":"888.00","goods_desc":"","img_name":"WYlqzgedvgUJfXVwjTU7OB7MRjAN1838aws44RNZ.jpeg","goods_salenum":0,"zan":0},{"goods_id":40,"goods_name":"嗯","goods_price":"5.00","goods_marketprice":"55.00","goods_desc":"","img_name":"BekaQ0OvEA8OpJkQrAg9meaRAztXVz9bmW6kqAmF.jpeg","goods_salenum":1,"zan":0}]}]
     * cart : {"nums":0,"amount":0}
     * pignjia_url : 8888888.com
     * shangjia_url : 99999999.com
     */

    public StoreInfoBean store_info;
    public boolean is_collect;
    public CartBean cart;
    public String comment_url;
    public String store_info_url;
    public List<ManjianBean> manjian;
    public List<GoodsListBean> goods_list;

    public String getComment_url() {
        return comment_url;
    }

    public void setComment_url(String comment_url) {
        this.comment_url = comment_url;
    }

    public String getStore_info_url() {
        return store_info_url;
    }

    public void setStore_info_url(String store_info_url) {
        this.store_info_url = store_info_url;
    }

    public StoreInfoBean getStore_info() {
        return store_info;
    }

    public void setStore_info(StoreInfoBean store_info) {
        this.store_info = store_info;
    }

    public boolean isIs_collect() {
        return is_collect;
    }

    public void setIs_collect(boolean is_collect) {
        this.is_collect = is_collect;
    }

    public CartBean getCart() {
        return cart;
    }

    public void setCart(CartBean cart) {
        this.cart = cart;
    }

    public List<ManjianBean> getManjian() {
        return manjian;
    }

    public void setManjian(List<ManjianBean> manjian) {
        this.manjian = manjian;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class StoreInfoBean {
        /**
         * store_id : 5
         * store_name : lijing店铺1
         * store_avatar : 7mnpQn1EEfPKD6TjPPnJi66AspqMLiLyi8wqKdqD.jpeg
         * store_sales : 0
         * store_credit : 0
         * store_description : 热滴我陪你走
         * daijinquan : 0
         */

        private int store_id;
        private String store_name;
        private String store_avatar;
        private int store_sales;
        private int store_credit;
        private String store_description;
        private int daijinquan;

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

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public int getStore_sales() {
            return store_sales;
        }

        public void setStore_sales(int store_sales) {
            this.store_sales = store_sales;
        }

        public int getStore_credit() {
            return store_credit;
        }

        public void setStore_credit(int store_credit) {
            this.store_credit = store_credit;
        }

        public String getStore_description() {
            return store_description;
        }

        public void setStore_description(String store_description) {
            this.store_description = store_description;
        }

        public int getDaijinquan() {
            return daijinquan;
        }

        public void setDaijinquan(int daijinquan) {
            this.daijinquan = daijinquan;
        }
    }

    public static class CartBean {
        /**
         * nums : 0
         * amount : 0
         */

        private int nums;
        private int amount;

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }

    public static class ManjianBean {
        /**
         * rule_id : 1
         * price : 10
         * discount : 5
         */

        private int rule_id;
        private int price;
        private int discount;

        public int getRule_id() {
            return rule_id;
        }

        public void setRule_id(int rule_id) {
            this.rule_id = rule_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }
    }

    public static class GoodsListBean {
        /**
         * stc_id : hot
         * stc_name : 热销
         * goods : [{"goods_id":40,"goods_name":"嗯","goods_price":"5.00","goods_marketprice":"55.00","goods_desc":"","img_name":"BekaQ0OvEA8OpJkQrAg9meaRAztXVz9bmW6kqAmF.jpeg","goods_salenum":1,"zan":0},{"goods_id":38,"goods_name":"111","goods_price":"1111.00","goods_marketprice":"111.00","goods_desc":"11","img_name":"11","goods_salenum":0,"zan":0},{"goods_id":39,"goods_name":"莫1","goods_price":"88.00","goods_marketprice":"888.00","goods_desc":"","img_name":"WYlqzgedvgUJfXVwjTU7OB7MRjAN1838aws44RNZ.jpeg","goods_salenum":0,"zan":0}]
         */

        private String stc_id;
        private String stc_name;
        private List<GoodsBean> goods;

        public String getStc_id() {
            return stc_id;
        }

        public void setStc_id(String stc_id) {
            this.stc_id = stc_id;
        }

        public String getStc_name() {
            return stc_name;
        }

        public void setStc_name(String stc_name) {
            this.stc_name = stc_name;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goods_id : 40
             * goods_name : 嗯
             * goods_price : 5.00
             * goods_marketprice : 55.00
             * goods_desc :
             * img_name : BekaQ0OvEA8OpJkQrAg9meaRAztXVz9bmW6kqAmF.jpeg
             * goods_salenum : 1
             * zan : 0
             */

            private int goods_id;
            private String goods_name;
            private String goods_price;
            private String goods_marketprice;
            private String goods_desc;
            private String img_name;
            private String goods_detail_url;
            private int goods_salenum;
            private int zan;

            public String getGoods_detail_url() {
                return goods_detail_url;
            }

            public void setGoods_detail_url(String goods_detail_url) {
                this.goods_detail_url = goods_detail_url;
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

            public String getGoods_marketprice() {
                return goods_marketprice;
            }

            public void setGoods_marketprice(String goods_marketprice) {
                this.goods_marketprice = goods_marketprice;
            }

            public String getGoods_desc() {
                return goods_desc;
            }

            public void setGoods_desc(String goods_desc) {
                this.goods_desc = goods_desc;
            }

            public String getImg_name() {
                return img_name;
            }

            public void setImg_name(String img_name) {
                this.img_name = img_name;
            }

            public int getGoods_salenum() {
                return goods_salenum;
            }

            public void setGoods_salenum(int goods_salenum) {
                this.goods_salenum = goods_salenum;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }
        }
    }
}
