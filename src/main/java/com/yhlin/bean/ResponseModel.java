package com.yhlin.bean;

import java.util.List;

public class ResponseModel {
    private String msg;
    private List<Object> data;

    public ResponseModel() {
    }

    public ResponseModel(String msg, List<Object> data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public List<Object> getData() {
        return data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
