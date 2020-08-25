package com.cjqb.caijiqianbao.utils;


import com.cjqb.caijiqianbao.http.JsonBean;

public class ObjectEntry extends JsonBean {
    private String code;
    private String msg;
    private ImageEntry data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ImageEntry getData() {
        return data;
    }

    public void setData(ImageEntry data) {
        this.data = data;
    }
}
