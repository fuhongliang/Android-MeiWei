package com.ifhu.meiwei.bean;

import java.util.List;

public class OrderinfoBean {

    /**
     * order_state : 10
     * store_name : lijing店铺1
     * store_phone : 13214214521
     * order_detail : [{"goods_id":5,"goods_name":"","goods_price":"10.00","goods_num":1,"goods_image":"","goods_marketprice":"88.00"},{"goods_id":5,"goods_name":"","goods_price":"10.00","goods_num":1,"goods_image":"","goods_marketprice":"88.00"}]
     * peisong : 2.00
     * manjian : 0.00
     * daijinquan : 0.00
     * total : 22.00
     * peisong_info : {"username":"嘻嘻嘻嘻嘻","address":"深圳市&nbsp;海关大厦","mobile":"45787455","sex":2}
     * order_info : {"order_sn":2000000000003801,"add_time":"2019-05-29 10:47:40","payment_code":"wxpay"}
     */

    private int order_state;
    private String store_name;
    private String store_phone;
    private String peisong;
    private String manjian;
    private String daijinquan;
    private String total;
    private PeisongInfoBean peisong_info;
    private OrderInfoBean order_info;
    private List<OrderDetailBean> order_detail;

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_phone() {
        return store_phone;
    }

    public void setStore_phone(String store_phone) {
        this.store_phone = store_phone;
    }

    public String getPeisong() {
        return peisong;
    }

    public void setPeisong(String peisong) {
        this.peisong = peisong;
    }

    public String getManjian() {
        return manjian;
    }

    public void setManjian(String manjian) {
        this.manjian = manjian;
    }

    public String getDaijinquan() {
        return daijinquan;
    }

    public void setDaijinquan(String daijinquan) {
        this.daijinquan = daijinquan;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PeisongInfoBean getPeisong_info() {
        return peisong_info;
    }

    public void setPeisong_info(PeisongInfoBean peisong_info) {
        this.peisong_info = peisong_info;
    }

    public OrderInfoBean getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoBean order_info) {
        this.order_info = order_info;
    }

    public List<OrderDetailBean> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrderDetailBean> order_detail) {
        this.order_detail = order_detail;
    }

    public static class PeisongInfoBean {
        /**
         * username : 嘻嘻嘻嘻嘻
         * address : 深圳市&nbsp;海关大厦
         * mobile : 45787455
         * sex : 2
         */

        private String username;
        private String address;
        private String mobile;
        private int sex;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }
    }

    public static class OrderInfoBean {
        /**
         * order_sn : 2000000000003801
         * add_time : 2019-05-29 10:47:40
         * payment_code : wxpay
         */

        private long order_sn;
        private String add_time;
        private String payment_code;

        public long getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(long order_sn) {
            this.order_sn = order_sn;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPayment_code() {
            return payment_code;
        }

        public void setPayment_code(String payment_code) {
            this.payment_code = payment_code;
        }
    }

    public static class OrderDetailBean {
        /**
         * goods_id : 5
         * goods_name :
         * goods_price : 10.00
         * goods_num : 1
         * goods_image :
         * goods_marketprice : 88.00
         */

        private int goods_id;
        private String goods_name;
        private String goods_price;
        private int goods_num;
        private String goods_image;
        private String goods_marketprice;

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

        public String getGoods_marketprice() {
            return goods_marketprice;
        }

        public void setGoods_marketprice(String goods_marketprice) {
            this.goods_marketprice = goods_marketprice;
        }
    }
}
