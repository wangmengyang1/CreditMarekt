package com.cjqb.caijiqianbao.bean.homeBean;
/**
  * Copyright 2019 bejson.com 
  */


import com.cjqb.caijiqianbao.http.JsonBean;

/**
 * Auto-generated: 2019-12-21 16:0:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class HomeJsonData extends JsonBean {

    private String code;
    private String msg;
    private HomeData homeData;
    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

    public void setData(HomeData homeData) {
         this.homeData = homeData;
     }
     public HomeData getData() {
         return homeData;
     }

}