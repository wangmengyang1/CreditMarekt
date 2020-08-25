package com.cjqb.caijiqianbao.bean.homeBean; /**
  * Copyright 2019 bejson.com 
  */

/**
 * Auto-generated: 2019-12-21 16:0:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Article {

    private String post_title;
    private String published_time;
    private String thumbnail;
    private String people_num;
    private String article_url;
    private int article_id;
    public String getPeople_num() {
        return people_num;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public void setPost_title(String post_title) {
         this.post_title = post_title;
     }
     public String getPost_title() {
         return post_title;
     }

    public void setPublished_time(String published_time) {
         this.published_time = published_time;
     }
     public String getPublished_time() {
         return published_time;
     }

    public void setThumbnail(String thumbnail) {
         this.thumbnail = thumbnail;
     }
     public String getThumbnail() {
         return thumbnail;
     }

    public void setArticle_id(int article_id) {
         this.article_id = article_id;
     }
     public int getArticle_id() {
         return article_id;
     }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }
}