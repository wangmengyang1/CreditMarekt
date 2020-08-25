package com.cjqb.caijiqianbao.bean.userBean;

import java.util.List;

public class UserData {
    private UserInfo user_info;
    private List<ServiceList> service_list;

    public UserInfo getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfo user_info) {
        this.user_info = user_info;
    }

    public List<ServiceList> getService_list() {
        return service_list;
    }

    public void setService_list(List<ServiceList> service_list) {
        this.service_list = service_list;
    }
}
