package com.cjqb.caijiqianbao.http;


import com.cjqb.caijiqianbao.application.myApplication;

/**
 * Created by cxf on 2018/9/17.
 */

public class CommonHttpUtil {


    /**
     * 初始化
     * @param myApplication
     */
    public static void init(myApplication myApplication) {

    }

    /**
     * 取消网络请求
     */
    public static void cancel(String tag) {
        HttpClient.getInstance().cancel(tag);
    }

    /**
     * 首页数据
     */
    public static void getHomeData(HttpCallback callback) {
        HttpClient.getInstance().get(CommonHttpConsts.INDEX, CommonHttpConsts.INDEX)
                .execute(callback);
//        OkGo.<HomeJsonData>get("http://upload.qq163.iego.cn:8088/cam")
//                .execute(new Callback<HomeJsonData>() {
//                    @Override
//                    public void onSuccess(Response<HomeJsonData> response) {
//
//                    }
//                });
    }

    /**
     *
     * @param callback
     */
    public static void getAliOrder(String parmas, HttpCallback callback) {
        HttpClient.getInstance().get(CommonHttpConsts.ARTUCLE)
                .execute(callback);
    }

    /**
     * 用微信支付充值 的时候在服务端生成订单号
     *
     * @param callback
     */
    public static void getWxOrder(String parmas, HttpCallback callback) {
        HttpClient.getInstance().get(parmas, CommonHttpConsts.GET_WX_ORDER)
                .execute(callback);
    }



//    //不做任何操作的HttpCallback
//    public static final HttpCallback NO_CALLBACK = new HttpCallback() {
//        @Override
//        public void onSuccess(JsonBean bean) {
//
//        }
//
//
//    };


}




