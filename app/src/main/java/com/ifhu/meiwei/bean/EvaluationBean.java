package com.ifhu.meiwei.bean;

import java.util.List;

public class EvaluationBean {

    /**
     * qishou : {"member_name":"张琪","avator":"xxxxx.jpg","time":"2019-01-02 12:01"}
     * info : {"goods_info":[{"goods_id":54,"goods_name":"鲨鱼"},{"goods_id":40,"goods_name":""}],"store_name":"果果生鲜","store_avatar":""}
     */

    private QishouBean qishou;
    private InfoBean info;

    public QishouBean getQishou() {
        return qishou;
    }

    public void setQishou(QishouBean qishou) {
        this.qishou = qishou;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class QishouBean {
        /**
         * member_name : 张琪
         * avator : xxxxx.jpg
         * time : 2019-01-02 12:01
         */

        private String member_name;
        private String avator;
        private String time;

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class InfoBean {
        /**
         * goods_info : [{"goods_id":54,"goods_name":"鲨鱼"},{"goods_id":40,"goods_name":""}]
         * store_name : 果果生鲜
         * store_avatar :
         */

        private String store_name;
        private String store_avatar;
        private List<GoodsInfoBean> goods_info;

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

        public List<GoodsInfoBean> getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(List<GoodsInfoBean> goods_info) {
            this.goods_info = goods_info;
        }

        public static class GoodsInfoBean {
            /**
             * goods_id : 54
             * goods_name : 鲨鱼
             */

            private int goods_id;
            private String goods_name;

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
        }
    }
}
