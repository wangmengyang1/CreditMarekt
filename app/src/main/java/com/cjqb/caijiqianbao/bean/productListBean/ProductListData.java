package com.cjqb.caijiqianbao.bean.productListBean;

import com.cjqb.caijiqianbao.bean.homeBean.Product;

import java.util.List;

public class ProductListData {
    private List<Product> product;
    public void setProduct(List<Product> product) {
        this.product = product;
    }
    public List<Product> getProduct() {
        return product;
    }
}
