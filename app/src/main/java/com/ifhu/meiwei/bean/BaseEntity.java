package com.ifhu.meiwei.bean;

/**
 *
 * @author KevinFu
 * @date 2019/5/17
 * Copyright (c) 2019 KevinFu
 */
public class BaseEntity<T> {
    public static String SUCCESS_CODE = "200";
    public String code = "200";
    public String msg;
    public T data;
    /**
     * Token失效、伪造等
     */
    public static final String TOKENMISSION_CODE = "3001";
    public static final String TOKENTFAKE_CODE = "3000";
    public static final String TOKENTIMEOUT_CODE = "3002";
    public boolean isTokenTimeOut() {
        switch (code){
            case TOKENMISSION_CODE:
            case TOKENTFAKE_CODE:
            case TOKENTIMEOUT_CODE:
                return true;
            default:
                return false;
        }
    }

    public boolean isSuccess() {
        if (SUCCESS_CODE.equals(code)) {
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
