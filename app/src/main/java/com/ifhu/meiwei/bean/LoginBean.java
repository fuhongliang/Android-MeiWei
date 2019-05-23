package com.ifhu.meiwei.bean;

public class LoginBean {

    /**
     * member_id : 25
     * member_mobile : 18594286622
     * member_name : 未设置_1558576106
     * member_avatar :
     * need_pwd : false
     * token : eyJpdiI6IjdGWDNCcEJNSnp3M2p6a3pYSXhlU0E9PSIsInZhbHVlIjoiVTNQQzJpMU9Veko4UmRnWWZKOW0wWWVDcWRUbitDZzl4N205MjlobktURT0iLCJtYWMiOiIxNDY4YmQ4NzQ4OWUzMDA2MzJmMTRiNDAxODg1YWI5YmQ3ZDI1OGFkMTkyMGU2MTc0N2VmMDU5MDA0OGI1MGFmIn0=
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
