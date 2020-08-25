package com.cjqb.caijiqianbao.bean.stepEnd;

public class Loan_info {

    private int id;
    private String loan_time;
    private String gross_interset;
    private String amount;
    private String interest;
    private String create_time;
    private String update_time;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setLoan_time(String loan_time) {
         this.loan_time = loan_time;
     }
     public String getLoan_time() {
         return loan_time;
     }

    public void setGross_interset(String gross_interset) {
         this.gross_interset = gross_interset;
     }
     public String getGross_interset() {
         return gross_interset;
     }

    public void setAmount(String amount) {
         this.amount = amount;
     }
     public String getAmount() {
         return amount;
     }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}