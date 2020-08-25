package com.cjqb.caijiqianbao.bean.homeBean;

import java.util.List;

public class Product {

    private String goods_name;
    private String goods_brief;
    private String goods_pic;
    private String goods_url;
    private int people_num;
    private String amount;
    private String goods_feature_term;
    private int goods_feature_hot;
    private int goods_feature_new;
    private int goods_feature_fast;
    private int product_id;
    private List<String> product_icon;

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_brief(String goods_brief) {
        this.goods_brief = goods_brief;
    }

    public String getGoods_brief() {
        return goods_brief;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public void setPeople_num(int people_num) {
        this.people_num = people_num;
    }

    public int getPeople_num() {
        return people_num;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public List<String> getProduct_icon() {
        return product_icon;
    }

    public void setProduct_icon(List<String> product_icon) {
        this.product_icon = product_icon;
    }

    public void setGoods_feature_term(String goods_feature_term) {
        this.goods_feature_term = goods_feature_term;
    }

    public String getGoods_feature_term() {
        return goods_feature_term;
    }

    public void setGoods_feature_hot(int goods_feature_hot) {
        this.goods_feature_hot = goods_feature_hot;
    }

    public int getGoods_feature_hot() {
        return goods_feature_hot;
    }

    public void setGoods_feature_new(int goods_feature_new) {
        this.goods_feature_new = goods_feature_new;
    }

    public int getGoods_feature_new() {
        return goods_feature_new;
    }

    public void setGoods_feature_fast(int goods_feature_fast) {
        this.goods_feature_fast = goods_feature_fast;
    }

    public int getGoods_feature_fast() {
        return goods_feature_fast;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

}