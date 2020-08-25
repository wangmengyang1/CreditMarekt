package com.cjqb.caijiqianbao.bean.appleHomeBean;

import com.cjqb.caijiqianbao.bean.homeBean.Banner;

import java.util.List;

public class Data {

    private List<Banner> banner;
    private Option_value option_value;

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setOption_value(Option_value option_value) {
        this.option_value = option_value;
    }

    public Option_value getOption_value() {
        return option_value;
    }
}
