package com.cjqb.caijiqianbao.bean.productBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class ProductJsonData extends JsonBean {

    private String code;
    private String msg;
    private ProductData data;

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

    public void setData(ProductData data) {
        this.data = data;
    }

    public ProductData getData() {
        return data;
    }
}
