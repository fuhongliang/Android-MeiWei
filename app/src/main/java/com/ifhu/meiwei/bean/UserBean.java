package com.ifhu.meiwei.bean;

/**
 * Created by KevinFu on 2019/5/17.
 * Copyright (c) 2019 KevinFu
 */
public class UserBean {

    /**
     * member_id : 2
     * member_mobile : 13763016638
     * member_name : 未设置_1558594134
     * member_avatar :
     * need_pwd : true
     * token : eyJpdiI6Im9oN3BqOEpwMUROb3ZueGVPNnRRa0E9PSIsInZhbHVlIjoidERMOHVVSDl2dU13QmZ5R3JPZ3pFYmxIbnErZGxWNmR2NElcLzF0Z0lmVE09IiwibWFjIjoiOTA3M2Q2MzVjZTA1NzM5ZTQ5MGRhMjYyZjE4YzM5NDZiYzk1MjhlMDkyNjlmZmY3Yzg5ZjdmYzRiOGI4MTY4OCJ9
     */

    private int member_id;
    private String member_mobile;
    private String member_name;
    private String member_avatar;
    private boolean need_pwd;
    private String token;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public boolean isNeed_pwd() {
        return need_pwd;
    }

    public void setNeed_pwd(boolean need_pwd) {
        this.need_pwd = need_pwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
