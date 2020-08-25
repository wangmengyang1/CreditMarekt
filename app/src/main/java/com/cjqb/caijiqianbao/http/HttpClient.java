package com.cjqb.caijiqianbao.http;

import android.app.Application;

import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.VersionUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by cxf on 2018/9/17.
 */

public class HttpClient {

    private static final int TIMEOUT = 10000;
    private static HttpClient sInstance;
    private OkHttpClient mOkHttpClient;
    private String mLanguage;//语言
    private String mUrl = "http://192.168.8.114/api/";

    public static HttpClient getInstance() {
        if (sInstance == null) {
            synchronized (HttpClient.class) {
                if (sInstance == null) {
                    sInstance = new HttpClient();
                }
            }
        }
        return sInstance;
    }

    public void init(Application applicationContext) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        //使用内存保持cookie，app退出后，cookie消失
        builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
//        //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new SPCookieStore(CommonAppContext.sInstance)));
//        //使用数据库保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(CommonAppContext.sInstance)));
        builder.retryOnConnectionFailure(true);
//        Dispatcher dispatcher = new Dispatcher();
//        dispatcher.setMaxRequests(20000);
//        dispatcher.setMaxRequestsPerHost(10000);
//        builder.dispatcher(dispatcher);

        //输出HTTP请求 响应信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("http");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(loggingInterceptor);
        mOkHttpClient = builder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept-Language", "zh-CN");
        headers.put("Connection", "keep-alive");
        headers.put(SpUtil.UID, SpUtil.getInstance().getStringValue(SpUtil.UID));
        headers.put(SpUtil.TOKEN, SpUtil.getInstance().getStringValue(SpUtil.TOKEN));
        headers.put("version", VersionUtil.getVersion(applicationContext));
        OkGo.getInstance().init(applicationContext)
                .setOkHttpClient(mOkHttpClient)
                .setCacheMode(CacheMode.NO_CACHE)
                .addCommonHeaders(headers)
                .setRetryCount(1);

    }

    public GetRequest<JsonBean> get(String serviceName, String tag) {
        return OkGo.<JsonBean>get(mUrl + serviceName)
                .headers("Connection", "keep-alive")
                .tag(tag)
                .params(CommonHttpConsts.LANGUAGE, mLanguage);
    }


    public GetRequest<JsonBean> get(String serviceName) {
        return OkGo.<JsonBean>get(mUrl + serviceName)
                .headers("Connection", "keep-alive")
                .tag(serviceName)
                .params(CommonHttpConsts.LANGUAGE, mLanguage);
    }

    public PostRequest<JsonBean> post(String serviceName, String tag) {
        return OkGo.<JsonBean>post(mUrl + serviceName)
                .headers("Connection", "keep-alive")
                .tag(tag)
                .params(CommonHttpConsts.LANGUAGE, mLanguage);
    }

    public void cancel(String tag) {
        OkGo.cancelTag(mOkHttpClient, tag);
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

}
