package com.cjqb.caijiqianbao.view;

import com.cjqb.caijiqianbao.http.JsonBean;

public class OrderEntry extends JsonBean {
    private String code;
    private String msg;
    private OrderItemEntry orderData;


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

    public OrderItemEntry getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderItemEntry orderData) {
        this.orderData = orderData;
    }

    public class OrderItemEntry{
        private String orderNumber;

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }
    }
}
