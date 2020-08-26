package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.appleHomeBean.AppleHomeJsonBean;
import com.cjqb.caijiqianbao.bean.appleHomeBean.Data;
import com.cjqb.caijiqianbao.bean.appleHomeBean.Option_value;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private Banner mBanner;
    private ArrayList<String> mBannerPath = new ArrayList<>();
    private ArrayList<String> mBannerClickUrl = new ArrayList<>();
    private TextView mTvQuate;
    private TextView mTvQuateRate;

    public static void forward(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mTvQuate = findViewById(R.id.tv_quota);
        mTvQuateRate = findViewById(R.id.tv_quota_interest_rate);
        getData();
        initFlipper();
    }

    private void getData() {
        OkGo.<AppleHomeJsonBean>post(HttpUrl.QUOTA)
                .execute(new CommonCallback<AppleHomeJsonBean>() {
                    @Override
                    public AppleHomeJsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), AppleHomeJsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<AppleHomeJsonBean> response) {
                        super.onSuccess(response);
                        AppleHomeJsonBean bean = response.body();
                        if (bean != null) {
                            Data data = bean.getData();
                            List<com.cjqb.caijiqianbao.bean.homeBean.Banner> banner = data.getBanner();
                            Option_value option_value = data.getOption_value();
                            String quota_value = option_value.getQuota_value();
                            String quota_interest_rate = option_value.getQuota_interest_rate();
                            mBannerPath.clear();
                            mBannerClickUrl.clear();
                            for (com.cjqb.caijiqianbao.bean.homeBean.Banner banner1 : banner) {
                                mBannerPath.add(banner1.getPic());
                                mBannerClickUrl.add(banner1.getTag_url());
                            }
                            initBanner();
                            mTvQuate.setText(quota_value + "");
                            mTvQuateRate.setText(quota_interest_rate + "");
                            SpUtil.getInstance().setStringValue(SpUtil.MAY_QUOTA,option_value.getMay_quota());
                        }

                    }
                });
    }

    private void initFlipper() {
        ViewFlipper mViewFlipper = findViewById(R.id.viewflipper);
        Animation anim_in_left = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);//左进
        Animation anim_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);//右出
        mViewFlipper.setInAnimation(anim_in_left);//添加进入动画效果
        mViewFlipper.setOutAnimation(anim_out_right);//添加退出动画效果
        List<String> data = new ArrayList<>();
        data.add("用户137****6546已成功领取");
        data.add("用户139****7905已成功领取");
        data.add("用户150****6437已成功领取");
        data.add("用户182****2628已成功领取");
        data.add("用户134****3105已成功领取");
        data.add("用户178****6849已成功领取");
        data.add("用户147****9871已成功领取");
        data.add("用户187****9134已成功领取");
        data.add("用户157****6129已成功领取");
        data.add("用户158****3452已成功领取");
        data.add("用户138****5781已成功领取");
        for (int i = 0; i < data.size(); i++) {//遍历图片资源
            TextView textView = new TextView(this);//创建ImageView对象
            textView.setText(data.get(i));//将遍历的图片保存在ImageView中
            mViewFlipper.addView(textView);//  //加载图片
        }
    }

    public void initBanner() {
        //放标题的集合
        mBanner = findViewById(R.id.banner);
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                setViewSize(mBanner, ScreenDimenUtil.getInstance().getScreenWdith(), (int) (ScreenDimenUtil.getInstance().getScreenWdith() * 0.48));
                ImgLoader.display(HomeActivity.this, path.toString(), imageView);
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int p) {
//                if (banner.get(p).getProduct_id() > 0) {
//                    if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
//                        LoginActivity.forward(HomeActivity.this);
//                    } else {
//                        ChanneInfoActivity.forward(HomeActivity.this, banner.get(p).getProduct_id());
//                    }
//                    return;
//                }
//                if (!TextUtils.isEmpty(mBannerClickUrl.get(p)))
//                    WebViewActivity.forward(getActivity(), mBannerClickUrl.get(p));
            }
        });
        showBanner();
    }

    private void showBanner() {
        if (mBannerPath == null || mBannerPath.size() == 0 || mBanner == null) {
            return;
        }
//        if (mBannerShowed) {
//            return;
//        }
//        mBannerShowed = true;
        mBanner.setImages(mBannerPath);
        mBanner.start();
    }

    /**
     * 设置控件大小
     */
    public void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public void gotoNext(View v) {
//        LoginActivity.forward(this);
    }
    public void gotoLogin(View v) {
//        LoginActivity.forward(this);
    }
}
