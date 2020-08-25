package com.cjqb.caijiqianbao.bean.loginBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class loginJsonData extends JsonBean {
    private String code;
    private String msg;
    private loginData data;

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

    public void setData(loginData data) {
        this.data = data;
    }

    public loginData getData() {
        return data;
    }
}
