package com.cjqb.caijiqianbao.view;

import com.cjqb.caijiqianbao.bean.stepEnd.Loan_info;

public class LoanEntry {
    private boolean isCheck;
    private Loan_info loan_info;


    public LoanEntry(boolean isCheck, Loan_info loan_info) {
        this.isCheck = isCheck;
        this.loan_info = loan_info;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Loan_info getLoan_info() {
        return loan_info;
    }

    public void setLoan_info(Loan_info loan_info) {
        this.loan_info = loan_info;
    }
}
