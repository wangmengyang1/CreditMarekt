package com.cjqb.caijiqianbao.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.AppleActivityStepEnd;
import com.cjqb.caijiqianbao.activity.AppleActivityStepFirst;
import com.cjqb.caijiqianbao.activity.AppleActivityStepThree;
import com.cjqb.caijiqianbao.activity.AppleActivityStepTwo;
import com.cjqb.caijiqianbao.activity.LoginActivity;
import com.cjqb.caijiqianbao.application.myApplication;
import com.cjqb.caijiqianbao.bean.appleHomeBean.AppleHomeJsonBean;
import com.cjqb.caijiqianbao.bean.appleHomeBean.Data;
import com.cjqb.caijiqianbao.bean.appleHomeBean.Option_value;
import com.cjqb.caijiqianbao.bean.loginBean.loginJsonData;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpClient;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeNewFragment extends Fragment {

    private Banner mBanner;
    private ArrayList<String> mBannerPath = new ArrayList<>();
    private ArrayList<String> mBannerClickUrl = new ArrayList<>();
    private TextView mTvQuate;
    private TextView mTvQuateRate;
    private ViewFlipper mViewFlipper;
    private View ll;
    private View view;
    private TextView tvGo;


    public static void forward(Context context) {
        Intent intent = new Intent(context, HomeNewFragment.class);
        context.startActivity(intent);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, null);
        mTvQuate = view.findViewById(R.id.tv_quota);
        mTvQuateRate = view.findViewById(R.id.tv_quota_interest_rate);
        mViewFlipper = view.findViewById(R.id.viewflipper);
        mBanner = view.findViewById(R.id.banner);
        ll = view.findViewById(R.id.ll);
        tvGo = view.findViewById(R.id.tv_go);
        view.findViewById(R.id.tv_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AppleActivityStepEnd.forward(getActivity(),"12313123123124");
                if (SpUtil.getInstance().getStringValue(SpUtil.IS_VIP).equals("1") && (SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN))) {
                    ToastUtil.show("你已成为会员，请进入我的权益进行操作");
                    return;
                }
                if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
                    LoginActivity.forward(getActivity());
                } else {
                    getverifystatus();

                }



            }
        });
        getData();
        initFlipper();

        return view;
    }
    public void init(){
        String stringValue = SpUtil.getInstance().getStringValue(SpUtil.VEST_TEMP);
        if ("2".equals(stringValue)) {
            ll.setBackgroundResource(R.mipmap.ic_home_new_head);
        } else {
            ll.setBackgroundResource(R.mipmap.ic_home_head);
        }

        if (SpUtil.getInstance().getStringValue(SpUtil.VEST_VALUE).equals("0")) {
            view.findViewById(R.id.img_is_v).setVisibility(View.GONE);
            view.findViewById(R.id.img_is_v1).setVisibility(View.GONE);
            view.findViewById(R.id.ll_is_v1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.ll_view_f).setVisibility(View.VISIBLE);
            tvGo.setText("去借钱");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void getverifystatus() {
        OkGo.<loginJsonData>post(HttpUrl.GETVERIFYSTATUS)
                .execute(new CommonCallback<loginJsonData>() {
                    @Override
                    public loginJsonData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), loginJsonData.class);
                    }

                    @Override
                    public void onSuccess(Response<loginJsonData> response) {
                        super.onSuccess(response);
                        loginJsonData bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                JSONObject user_info = bean.getData().getUser_info();
                                Map<String, String> map = new HashMap<>();
                                map.put(SpUtil.UID, bean.getData().getUid());
                                map.put(SpUtil.TOKEN, bean.getData().getToken());
                                SpUtil.getInstance().setMultiStringValue(map);
                                SpUtil.getInstance().setBooleanValue(SpUtil.IS_LOGIN, true);
                                SpUtil.getInstance().setStringValue(SpUtil.PHONE, user_info.getString("mobile"));

                                String name = user_info.getString("name");
                                String id_number = user_info.getString("id_number");
                                String bank_mobile = user_info.getString("bank_mobile");
                                String bank_card_number = user_info.getString("bank_card_number");

                                SpUtil.getInstance().setStringValue(SpUtil.NAME, name);
                                SpUtil.getInstance().setStringValue(SpUtil.ID_NUMBER, id_number);
                                SpUtil.getInstance().setStringValue(SpUtil.BANK_MOBILE, bank_mobile);
                                SpUtil.getInstance().setStringValue(SpUtil.BANK_CARD_NUMBER, bank_card_number);
                                String stage = bean.getData().getStage();
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, stage);
                                SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, user_info.getInteger("is_vip").toString());

//
                                if ("BasicInfo".equals(stage)) {
                                    AppleActivityStepFirst.forward(getActivity());
                                } else if ("ContactInfo".equals(stage)) {
                                    AppleActivityStepTwo.forward(getActivity());
                                } else if ("BankInfo".equals(stage)) {
                                    AppleActivityStepThree.forward(getActivity());
                                } else if ("OrderInfo".equals(stage)) {
                                    AppleActivityStepEnd.forward(getActivity(), SpUtil.getInstance().getStringValue(SpUtil.BANK_CARD_NUMBER));
                                }
                            }
                        }

                    }
                });
    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        mTvQuate = findViewById(R.id.tv_quota);
//        mTvQuateRate = findViewById(R.id.tv_quota_interest_rate);
//        getData();
//        initFlipper();
//    }

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
                            SpUtil.getInstance().setStringValue(SpUtil.MAY_QUOTA, option_value.getMay_quota());
                        }

                    }
                });
    }

    private void initFlipper() {

        Animation anim_in_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);//左进
        Animation anim_out_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);//右出
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
            TextView textView = new TextView(getActivity());//创建ImageView对象
            textView.setText(data.get(i));//将遍历的图片保存在ImageView中
            mViewFlipper.addView(textView);//  //加载图片
        }
    }

    public void initBanner() {
        //放标题的集合

        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                setViewSize(mBanner, ScreenDimenUtil.getInstance().getScreenWdith(), (int) (ScreenDimenUtil.getInstance().getScreenWdith() * 0.48));
                ImgLoader.display(getActivity(), path.toString(), imageView);
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










}
