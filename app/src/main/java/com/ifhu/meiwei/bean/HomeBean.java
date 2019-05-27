package com.ifhu.meiwei.bean;

import java.util.List;

public class HomeBean {

    /**
     * title :
     * image_name : huiewhfwehff.jpg
     * link_url :
     */

    private String title;
    private String image_name;
    private String link_url;
    /**
     * gc_name : 美食
     * gc_id : 1057
     * icon_image :
     */

    private String gc_name;
    private int gc_id;
    private String icon_image;
    /**
     * type : 1
     * background_image :
     * brief :
     */

    private String type;
    private String background_image;
    private String brief;
    /**
     * store_id : 2
     * store_name : 店铺1
     * store_avatar : 7mnpQn1EEfPKD6TjPPnJi66AspqMLiLyi8wqKdqD.jpeg
     * store_sales : 0
     * store_credit : 0
     * xianshi : []
     * manjian : []
     */

    private int store_id;
    private String store_name;
    private String store_avatar;
    private int store_sales;
    private int store_credit;
    private List<?> xianshi;
    private List<?> manjian;

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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }


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

    public List<?> getXianshi() {
        return xianshi;
    }

    public void setXianshi(List<?> xianshi) {
        this.xianshi = xianshi;
    }

    public List<?> getManjian() {
        return manjian;
    }

    public void setManjian(List<?> manjian) {
        this.manjian = manjian;
    }
}
