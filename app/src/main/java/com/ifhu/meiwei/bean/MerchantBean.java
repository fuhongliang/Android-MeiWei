package com.ifhu.meiwei.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuhongliang
 */
public class MerchantBean implements Parcelable {


    /**
     * store_info : {"store_id":2,"store_name":"多味丫","store_avatar":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/625fb8226c6703dfbe523897b0c7884c.jpg","store_sales":0,"store_credit":0,"store_description":""}
     * is_collect : false
     * manjian : [{"price":1000,"discount":900}]
     * goods_list : [{"stc_id":"hot","stc_name":"热销","cart_nums":0,"goods":[{"goods_id":6,"goods_name":"女人斌","goods_price":"33.00","goods_marketprice":"36.00","goods_desc":"","img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg","goods_salenum":0,"store_id":2,"zan":0,"goods_detail_url":"http://47.111.27.189:88/users/#/p_detail/2/6/"}]},{"stc_id":"22","stc_name":"水果","cart_nums":0,"goods":[{"goods_id":8,"goods_name":"水果茶","goods_price":"60.00","goods_marketprice":"80.00","goods_desc":"","img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/85592e0945d66340f40559b6077ad43a.jpg","goods_salenum":0,"store_id":2,"zan":0,"goods_detail_url":"http://47.111.27.189:88/users/#/p_detail/2/8/6"}]},{"stc_id":"1","stc_name":"女人味","cart_nums":1,"goods":[{"goods_id":6,"goods_name":"女人斌","goods_price":"33.00","goods_marketprice":"36.00","goods_desc":"","img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg","goods_salenum":0,"store_id":2,"zan":0,"goods_detail_url":"http://47.111.27.189:88/users/#/p_detail/2/6/6"},{"goods_id":7,"goods_name":"女女的","goods_price":"33.00","goods_marketprice":"69.00","goods_desc":"","img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/5b14107eebdf0c59ea516fc484cfb789.jpg","goods_salenum":0,"store_id":2,"zan":0,"goods_detail_url":"http://47.111.27.189:88/users/#/p_detail/2/7/6"}]}]
     * cart : {"peisong":5,"goods":[{"goods_num":2,"img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg","goods_price":"33.00","goods_name":"女人斌"}]}
     * comment_url : http://47.111.27.189:88/users/#/evaluate/2/evaluateall
     * store_info_url : http://47.111.27.189:88/users/#/business/2
     */

    private StoreInfoBean store_info;
    private boolean is_collect;
    private CartBean cart;
    private String comment_url;
    private String store_info_url;
    private List<ManjianBean> manjian;
    private List<GoodsListBean> goods_list;


    protected MerchantBean(Parcel in) {
        store_info = in.readParcelable(StoreInfoBean.class.getClassLoader());
        is_collect = in.readByte() != 0;
        comment_url = in.readString();
        store_info_url = in.readString();
        manjian = in.createTypedArrayList(ManjianBean.CREATOR);
        goods_list = in.createTypedArrayList(GoodsListBean.CREATOR);
    }

    public static final Creator<MerchantBean> CREATOR = new Creator<MerchantBean>() {
        @Override
        public MerchantBean createFromParcel(Parcel in) {
            return new MerchantBean(in);
        }

        @Override
        public MerchantBean[] newArray(int size) {
            return new MerchantBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (is_collect ? 1 : 0));
        parcel.writeString(comment_url);
        parcel.writeString(store_info_url);
    }

    public static class StoreInfoBean implements Parcelable{
        /**
         * store_id : 2
         * store_name : 多味丫
         * store_avatar : http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/625fb8226c6703dfbe523897b0c7884c.jpg
         * store_sales : 0
         * store_credit : 0
         * store_description :
         */

        private int store_id;
        private String store_name;
        private String store_avatar;
        private int store_sales;
        private int store_credit;
        private String store_description;

        protected StoreInfoBean(Parcel in) {
            store_id = in.readInt();
            store_name = in.readString();
            store_avatar = in.readString();
            store_sales = in.readInt();
            store_credit = in.readInt();
            store_description = in.readString();
        }

        public static final Creator<StoreInfoBean> CREATOR = new Creator<StoreInfoBean>() {
            @Override
            public StoreInfoBean createFromParcel(Parcel in) {
                return new StoreInfoBean(in);
            }

            @Override
            public StoreInfoBean[] newArray(int size) {
                return new StoreInfoBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(store_id);
            parcel.writeString(store_name);
            parcel.writeString(store_avatar);
            parcel.writeInt(store_sales);
            parcel.writeInt(store_credit);
            parcel.writeString(store_description);
        }
    }

    public static class CartBean {
        /**
         * peisong : 5
         * goods : [{"goods_num":2,"img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg","goods_price":"33.00","goods_name":"女人斌"}]
         */

        private int peisong;
        private List<GoodsBean> goods;

        public int getPeisong() {
            return peisong;
        }

        public void setPeisong(int peisong) {
            this.peisong = peisong;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * goods_num : 2
             * img_name : http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg
             * goods_price : 33.00
             * goods_name : 女人斌
             */

            private int goods_num;
            private String goods_id;
            private String img_name;
            private String goods_price;
            private String goods_name;

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public int getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(int goods_num) {
                this.goods_num = goods_num;
            }

            public String getImg_name() {
                return img_name;
            }

            public void setImg_name(String img_name) {
                this.img_name = img_name;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }
        }
    }

    public static class ManjianBean implements Parcelable{
        /**
         * price : 1000
         * discount : 900
         */

        private int price;
        private int discount;

        protected ManjianBean(Parcel in) {
            price = in.readInt();
            discount = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(price);
            dest.writeInt(discount);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ManjianBean> CREATOR = new Creator<ManjianBean>() {
            @Override
            public ManjianBean createFromParcel(Parcel in) {
                return new ManjianBean(in);
            }

            @Override
            public ManjianBean[] newArray(int size) {
                return new ManjianBean[size];
            }
        };

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

    public static class GoodsListBean implements Parcelable{
        /**
         * stc_id : hot
         * stc_name : 热销
         * cart_nums : 0
         * goods : [{"goods_id":6,"goods_name":"女人斌","goods_price":"33.00","goods_marketprice":"36.00","goods_desc":"","img_name":"http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg","goods_salenum":0,"store_id":2,"zan":0,"goods_detail_url":"http://47.111.27.189:88/users/#/p_detail/2/6/"}]
         */

        private String stc_id;
        private String stc_name;
        private int cart_nums;
        private List<GoodsBeanX> goods;

        protected GoodsListBean(Parcel in) {
            stc_id = in.readString();
            stc_name = in.readString();
            cart_nums = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(stc_id);
            dest.writeString(stc_name);
            dest.writeInt(cart_nums);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
            @Override
            public GoodsListBean createFromParcel(Parcel in) {
                return new GoodsListBean(in);
            }

            @Override
            public GoodsListBean[] newArray(int size) {
                return new GoodsListBean[size];
            }
        };

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

        public int getCart_nums() {
            return cart_nums;
        }

        public void setCart_nums(int cart_nums) {
            this.cart_nums = cart_nums;
        }

        public List<GoodsBeanX> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBeanX> goods) {
            this.goods = goods;
        }

        public static class GoodsBeanX {
            /**
             * goods_id : 6
             * goods_name : 女人斌
             * goods_price : 33.00
             * goods_marketprice : 36.00
             * goods_desc :
             * img_name : http://goods-images-bucket.oss-cn-beijing.aliyuncs.com/f7fc395b80b1aca1b1bb0318810fd050.jpg
             * goods_salenum : 0
             * store_id : 2
             * zan : 0
             * goods_detail_url : http://47.111.27.189:88/users/#/p_detail/2/6/
             */

            private int goods_id;
            private int goods_number_in_cart = 0;
            private String goods_name;
            private String goods_price;
            private String goods_marketprice;
            private String goods_desc;
            private String img_name;
            private int goods_salenum;
            private int store_id;
            private int zan;
            private String goods_detail_url;

            public int getGoods_number_in_cart() {
                return goods_number_in_cart;
            }

            public void setGoods_number_in_cart(int goods_number_in_cart) {
                this.goods_number_in_cart = goods_number_in_cart;
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

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public String getGoods_detail_url() {
                return goods_detail_url;
            }

            public void setGoods_detail_url(String goods_detail_url) {
                this.goods_detail_url = goods_detail_url;
            }
        }
    }
}
