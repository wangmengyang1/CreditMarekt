package com.cjqb.caijiqianbao.bean.productListBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class ProductListJsonData extends JsonBean {
    private String code;
    private String msg;
    private ProductListData data;
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

    public void setData(ProductListData data) {
        this.data = data;
    }
    public ProductListData getData() {
        return data;
    }
}
