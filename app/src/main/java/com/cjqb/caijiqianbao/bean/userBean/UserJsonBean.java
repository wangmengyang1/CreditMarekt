package com.cjqb.caijiqianbao.bean.userBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class UserJsonBean extends JsonBean {
    private String code;
    private String msg;
    private UserData data;
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

    public void setData(UserData data) {
        this.data = data;
    }
    public UserData getData() {
        return data;
    }
}
