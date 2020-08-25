package com.cjqb.caijiqianbao.bean.homeBean; /**
 * Copyright 2019 bejson.com
 */

import java.util.List;

/**
 * Auto-generated: 2019-12-21 16:0:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HomeData {

    private List<Product_class> product_class;
    private List<Banner> banner;
    private List<Product> product;
    private List<Hot_list> hot_list;
    private List<Product> recommend_list;
    private List<Article> article;
    private int product_detail_off;

    public int getProduct_detail_off() {
        return product_detail_off;
    }

    public List<Product> getRecommend_list() {
        return recommend_list;
    }

    public void setRecommend_list(List<Product> recommend_list) {
        this.recommend_list = recommend_list;
    }

    public void setProduct_detail_off(int product_detail_off) {
        this.product_detail_off = product_detail_off;
    }

    public void setProduct_class(List<Product_class> product_class) {
        this.product_class = product_class;
    }

    public List<Product_class> getProduct_class() {
        return product_class;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setHot_list(List<Hot_list> hot_list) {
        this.hot_list = hot_list;
    }

    public List<Hot_list> getHot_list() {
        return hot_list;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

    public List<Article> getArticle() {
        return article;
    }

}