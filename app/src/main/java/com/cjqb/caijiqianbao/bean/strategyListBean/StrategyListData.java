package com.cjqb.caijiqianbao.bean.strategyListBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class StrategyListData extends JsonBean {
    private String code;
    private String msg;
    private StrategyData data;
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

    public void setData(StrategyData data) {
        this.data = data;
    }
    public StrategyData getData() {
        return data;
    }
}
