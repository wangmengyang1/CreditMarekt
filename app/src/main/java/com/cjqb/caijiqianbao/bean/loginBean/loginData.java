package com.cjqb.caijiqianbao.bean.loginBean;

import com.alibaba.fastjson.JSONObject;

public class loginData {
    private String uid;
    private String token;

    private String stage;
    private JSONObject user_info;

    public JSONObject getUser_info() {
        return user_info;
    }

    public void setUser_info(JSONObject user_info) {
        this.user_info = user_info;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

}
