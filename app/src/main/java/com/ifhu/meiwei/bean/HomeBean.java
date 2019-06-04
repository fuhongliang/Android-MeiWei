package com.ifhu.meiwei.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

/**
 * @author fuhongliang
 */
public class HomeBean {

    private List<BannerDataBean> banner_data;
    private List<GcsortDataBean> gcsort_data;
    private List<DiscountDataBean> discount_data;
    private List<StorelistDataBean> storelist_data;

    public List<BannerDataBean> getBanner_data() {
        return banner_data;
    }

    public void setBanner_data(List<BannerDataBean> banner_data) {
        this.banner_data = banner_data;
    }

    public List<GcsortDataBean> getGcsort_data() {
        return gcsort_data;
    }

    public void setGcsort_data(List<GcsortDataBean> gcsort_data) {
        this.gcsort_data = gcsort_data;
    }

    public List<DiscountDataBean> getDiscount_data() {
        return discount_data;
    }

    public void setDiscount_data(List<DiscountDataBean> discount_data) {
        this.discount_data = discount_data;
    }

    public List<StorelistDataBean> getStorelist_data() {
        return storelist_data;
    }

    public void setStorelist_data(List<StorelistDataBean> storelist_data) {
        this.storelist_data = storelist_data;
    }

    public static class BannerDataBean  extends SimpleBannerInfo {
        /**
         * title :
         * image_name : huiewhfwehff.jpg
         * link_url :
         */

        private String title;
        private String image_name;
        private String link_url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage_name() {
            return image_name;
        }

        public void setImage_name(String image_name) {
            this.image_name = image_name;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        @Override
        public Object getXBannerUrl() {
            return getLink_url();
        }
    }

    public static class GcsortDataBean {
        /**
         * gc_name : 美食
         * gc_id : 1057
         * icon_image :
         */

        private String gc_name;
        private int gc_id;
        private String icon_image;

        public String getGc_name() {
            return gc_name;
        }

        public void setGc_name(String gc_name) {
            this.gc_name = gc_name;
        }

        public int getGc_id() {
            return gc_id;
        }

        public void setGc_id(int gc_id) {
            this.gc_id = gc_id;
        }

        public String getIcon_image() {
            return icon_image;
        }

        public void setIcon_image(String icon_image) {
            this.icon_image = icon_image;
        }
    }

    public static class DiscountDataBean {
        /**
         * type : 1
         * background_image : X6BEBnfaUbVgwMLt9lJ5CdgiyqEdnDodBycHSx6l.jpeg
         * title : ????
         * brief :
         */

        private String type;
        private String background_image;
        private String title;
        private String brief;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBackground_image() {
            return background_image;
        }

        public void setBackground_image(String background_image) {
            this.background_image = background_image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }
    }

    public static class StorelistDataBean {
        /**
         * store_id : 4
         * store_name : 店铺
         * store_avatar : 7mnpQn1EEfPKD6TjPPnJi66AspqMLiLyi8wqKdqD.jpeg
         * store_sales : 0
         * store_credit : 0
         * xianshi : [{"xianshi_name":"","xianshi_id":1}]
         * manjian : [{"rule_id":1,"price":10,"discount":5},{"rule_id":2,"price":20,"discount":2},{"rule_id":3,"price":30,"discount":3}]
         */

        private int store_id;
        private String store_name;
        private String store_avatar;
        private int store_sales;
        private int store_credit;
        private List<XianshiBean> xianshi;
        private List<ManjianBean> manjian;

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

        public List<XianshiBean> getXianshi() {
            return xianshi;
        }

        public void setXianshi(List<XianshiBean> xianshi) {
            this.xianshi = xianshi;
        }

        public List<ManjianBean> getManjian() {
            return manjian;
        }

        public void setManjian(List<ManjianBean> manjian) {
            this.manjian = manjian;
        }

        public static class XianshiBean {
            /**
             * xianshi_name :
             * xianshi_id : 1
             */

            private String xianshi_name;
            private int xianshi_id;

            public String getXianshi_name() {
                return xianshi_name;
            }

            public void setXianshi_name(String xianshi_name) {
                this.xianshi_name = xianshi_name;
            }

            public int getXianshi_id() {
                return xianshi_id;
            }

            public void setXianshi_id(int xianshi_id) {
                this.xianshi_id = xianshi_id;
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
    }
}
