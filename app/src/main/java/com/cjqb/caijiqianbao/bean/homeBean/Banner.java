package com.cjqb.caijiqianbao.bean.homeBean;

/**
 * Auto-generated: 2019-12-21 16:0:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Banner {

    private String content;
    private String pic;
    private String tag_url;
    private int banner_id;
    private int product_id;
    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setPic(String pic) {
         this.pic = pic;
     }
     public String getPic() {
         return pic;
     }

    public void setTag_url(String tag_url) {
         this.tag_url = tag_url;
     }
     public String getTag_url() {
         return tag_url;
     }

    public void setBanner_id(int banner_id) {
         this.banner_id = banner_id;
     }
     public int getBanner_id() {
         return banner_id;
     }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}