package com.cjqb.caijiqianbao.bean.stepEnd;

import com.cjqb.caijiqianbao.http.JsonBean;

import java.util.List;

public class AppleBean extends JsonBean {

    private String code;
    private String msg;
    private Option option;
    private List<Loan_info> loan_info;
    private List<Loan_purpose> loan_purpose;
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

    public void setOption(Option option) {
         this.option = option;
     }
     public Option getOption() {
         return option;
     }

    public void setLoan_info(List<Loan_info> loan_info) {
         this.loan_info = loan_info;
     }
     public List<Loan_info> getLoan_info() {
         return loan_info;
     }

    public void setLoan_purpose(List<Loan_purpose> loan_purpose) {
         this.loan_purpose = loan_purpose;
     }
     public List<Loan_purpose> getLoan_purpose() {
         return loan_purpose;
     }

}