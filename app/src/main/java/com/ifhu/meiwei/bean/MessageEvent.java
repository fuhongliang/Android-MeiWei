package com.ifhu.meiwei.bean;

import java.util.ArrayList;

public class MessageEvent {
    private String message;
    private String data;

    private ArrayList<String> mArrayList;

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<String> getArrayList() {
        return mArrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        mArrayList = arrayList;
    }

    public MessageEvent(String message) {
        this.message = message;
    }

    public MessageEvent(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public MessageEvent(String message, ArrayList<String> mArrayList) {
        this.message = message;
        this.mArrayList = mArrayList;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
