package com.ifhu.meiwei.bean;

public class EditadreessBean {

    /**
     * address_id : 1
     * area_info : 河北	邢台市	内丘县
     * address : 3213213
     * mob_phone : 18594286623
     * sex : 1
     * true_name : 32312321
     * is_default : 0
     */

    private int address_id;
    private String area_info;
    private String address;
    private String mob_phone;
    private int sex;
    private String true_name;
    private int is_default;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMob_phone() {
        return mob_phone;
    }

    public void setMob_phone(String mob_phone) {
        this.mob_phone = mob_phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
