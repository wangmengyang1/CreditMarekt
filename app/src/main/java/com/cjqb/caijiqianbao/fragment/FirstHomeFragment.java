package com.cjqb.caijiqianbao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.activity.LoginActivity;
import com.cjqb.caijiqianbao.activity.ProFileInformationActivity;
import com.cjqb.caijiqianbao.bean.appleHomeBean.AppleHomeJsonBean;
import com.cjqb.caijiqianbao.bean.appleHomeBean.Data;
import com.cjqb.caijiqianbao.bean.appleHomeBean.Option_value;
import com.cjqb.caijiqianbao.bean.homeBean.Banner;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class FirstHomeFragment extends Fragment {

    private TextView getInstantApp;
    private TextView first_home_frag;


    private TextView firstAmount;
    private TextView secondAmount;
    private TextView ThreadAmount;
    private TextView FourAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_home_fragment , container , false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getInstantApp = view.findViewById(R.id.get_instant_app);
        first_home_frag= view.findViewById(R.id.first_home_frag);
        firstAmount= view.findViewById(R.id.money_first);
        secondAmount= view.findViewById(R.id.money_second);
        ThreadAmount= view.findViewById(R.id.money_thread);
        FourAmount= view.findViewById(R.id.money_four);
        getInstantApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SpUtil.getInstance().getBooleanValue(SpUtil.IS_LOGIN)) {
                    LoginActivity.forward(getActivity());
                }else {
                    Intent intent = new Intent(getActivity() , ProFileInformationActivity.class);
                    intent.putExtra("STAGE" ,  SpUtil.getInstance().getStringValue(SpUtil.STAGE));
                    getActivity().startActivity(intent);
                }
            }
        });
        getData();



        firstAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstAmount.setBackgroundResource(R.drawable.bg_20dp_fdc800_shape);
                secondAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                ThreadAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                FourAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);

                first_home_frag.setText("upto  ₹ 5000" );
            }
        });


        secondAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                secondAmount.setBackgroundResource(R.drawable.bg_20dp_fdc800_shape);
                ThreadAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                FourAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                first_home_frag.setText("upto  ₹ 10000" );
            }
        });


        ThreadAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                secondAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                ThreadAmount.setBackgroundResource(R.drawable.bg_20dp_fdc800_shape);
                FourAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                first_home_frag.setText("upto  ₹ 20000" );
            }
        });


        FourAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                secondAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                ThreadAmount.setBackgroundResource(R.drawable.bg_20dp_f9efca_shape);
                FourAmount.setBackgroundResource(R.drawable.bg_20dp_fdc800_shape);
                first_home_frag.setText("upto  ₹ 50000" );
            }
        });
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
//                            List<Banner> banner = data.getBanner();
                            Option_value option_value = data.getOption_value();
                            String quota_value = option_value.getQuota_value();
//                            String quota_interest_rate = option_value.getQuota_interest_rate();
//                            mBannerPath.clear();
//                            mBannerClickUrl.clear();
//                            for (com.cjqb.caijiqianbao.bean.homeBean.Banner banner1 : banner) {
//                                mBannerPath.add(banner1.getPic());
//                                mBannerClickUrl.add(banner1.getTag_url());
//                            }
//                            initBanner();
                            first_home_frag.setText("upto  ₹" + quota_value);

//                            mTvQuateRate.setText(quota_interest_rate + "");
                            SpUtil.getInstance().setStringValue(SpUtil.LOAN_AMOUNT, option_value.getMay_quota());
                        }

                    }
                });
    }
}
