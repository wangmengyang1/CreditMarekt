package com.cjqb.caijiqianbao.bean.bankBean;

import com.cjqb.caijiqianbao.bean.BankInfoBean;
import com.cjqb.caijiqianbao.http.JsonBean;

import java.util.List;

public class BankJsonBean extends JsonBean {
    private String code;
    private String msg;
    private List<BankInfoBean> data;
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

    @Override
    public List<BankInfoBean> getData() {
        return data;
    }

    public void setData(List<BankInfoBean> data) {
        this.data = data;
    }
}
