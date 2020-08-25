package com.cjqb.caijiqianbao.bean.stepEnd;

public class Option {

    private String quota_value;
    private String quota_interest_rate;
    private String may_quota;
    private String bank_name;// null,
    private String bank_card_number;//": null

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_card_number() {
        return bank_card_number;
    }

    public void setBank_card_number(String bank_card_number) {
        this.bank_card_number = bank_card_number;
    }

    public void setQuota_value(String quota_value) {
        this.quota_value = quota_value;
    }

    public String getQuota_value() {
        return quota_value;
    }

    public void setQuota_interest_rate(String quota_interest_rate) {
        this.quota_interest_rate = quota_interest_rate;
    }

    public String getQuota_interest_rate() {
        return quota_interest_rate;
    }

    public void setMay_quota(String may_quota) {
        this.may_quota = may_quota;
    }

    public String getMay_quota() {
        return may_quota;
    }

}