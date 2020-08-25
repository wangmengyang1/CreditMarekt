package com.cjqb.caijiqianbao.bean;

public class BindData {
    private String smsSendNo;// "",
    private String resultMsg;// "交易缺少必传参数或格式非法：参数name为必填项",
    private String resultCode;// "0005",
    private String         merOrderId;// "20200424053057sms98827",
    private String retFlag;// "F"

    public String getMerOrderId() {
        return merOrderId;
    }

    public void setMerOrderId(String merOrderId) {
        this.merOrderId = merOrderId;
    }

    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }

    public String getSmsSendNo() {
        return smsSendNo;
    }

    public void setSmsSendNo(String smsSendNo) {
        this.smsSendNo = smsSendNo;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
