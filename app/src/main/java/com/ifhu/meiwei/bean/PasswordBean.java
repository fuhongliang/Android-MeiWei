package com.ifhu.meiwei.bean;

public class PasswordBean {

    /**
     * member_id : 25
     * member_mobile : 18594286622
     * member_name : 未设置_1558576106
     * member_avatar :
     * token : eyJpdiI6InVtUEtOSDlzOXhFTUpJd1pwRG9Wb3c9PSIsInZhbHVlIjoiTWZtaHRLbUtJb1Q2QWpMZWhDNWdWckYyOUo0ZUttWGdrWUtoekd2RWJhWT0iLCJtYWMiOiI2ZWNjMzg3MTY5MDNiZTcyOTM5YTQ3ZDBlOWVhODMyYTAzNWU5MjZhMjM0NGQ4ZTY0MjJmNTY3ZWZmZTQ5ODdkIn0=
     */

    private int member_id;
    private String member_mobile;
    private String member_name;
    private String member_avatar;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
