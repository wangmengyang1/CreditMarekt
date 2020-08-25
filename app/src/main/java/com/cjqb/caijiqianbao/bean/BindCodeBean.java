package com.cjqb.caijiqianbao.bean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class BindCodeBean extends JsonBean {
    private boolean result;
    private String code;
    private String msg;
    private BindData data;
    public Object daaaaaaaaaa;
    public void setResult(boolean result) {
        this.result = result;
    }
    public boolean getResult() {
        return result;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public boolean isResult() {
        return result;
    }

    public BindData getData() {
        return data;
    }

    public void setData(BindData data) {
        this.data = data;
    }
}
