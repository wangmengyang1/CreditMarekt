package com.cjqb.caijiqianbao.bean.cityBean;

import java.util.List;

public class JsonCityBean {
    public String name;
    public List<Shi> city;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCity(List<Shi> city) {
        this.city = city;
    }
    public List<Shi> getCity() {
        return city;
    }
}
