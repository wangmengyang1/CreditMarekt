package com.cjqb.caijiqianbao.bean.vestBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class VestBean extends JsonBean {
    private String code;
    private String msg;
    private VestData data;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public VestData getData() {
        return data;
    }

    public void setData(VestData data) {
        this.data = data;
    }
}
