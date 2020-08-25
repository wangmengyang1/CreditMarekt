package com.cjqb.caijiqianbao.bean;

import java.io.Serializable;

public class LoginEntry implements Serializable {
    private String stage;

    public LoginEntry(String stage) {
        this.stage = stage;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
