package com.cjqb.caijiqianbao.bean.stepEnd;

public class Loan_purpose {

    private int id;
    private String loan_purpose;
    private String create_time;
    private String update_time;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setLoan_purpose(String loan_purpose) {
         this.loan_purpose = loan_purpose;
     }
     public String getLoan_purpose() {
         return loan_purpose;
     }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }


}