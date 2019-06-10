package com.ifhu.meiwei.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fuhongliang on 16/11/18.
 */

public class WXPayBean implements Serializable {

    /**
     * prepayid : wx10144848130795d978cc03491076889800
     * appid : wxbcdccb2c1d95e94b
     * partnerid : 1538920881
     * package : Sign=WXPay
     * noncestr : 99927c60ebe600c214a666d5b32803a3
     * timestamp : 1560149328
     * sign : 17B7546A58B9621D06319E93B93BE977
     */

    private String prepayid;
    private String appid;
    private String partnerid;
    @SerializedName("package")
    private String packageX;
    private String noncestr;
    private int timestamp;
    private String sign;

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
