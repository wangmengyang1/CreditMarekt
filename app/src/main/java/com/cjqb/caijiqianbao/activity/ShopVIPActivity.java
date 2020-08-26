package com.cjqb.caijiqianbao.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.BindCodeBean;
import com.cjqb.caijiqianbao.bean.stepEnd.AppleBean;
import com.cjqb.caijiqianbao.bean.stepEnd.Loan_info;
import com.cjqb.caijiqianbao.bean.stepEnd.Option;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.cjqb.caijiqianbao.utils.ValidatePhoneUtil;
import com.cjqb.caijiqianbao.utils.WordUtil;
import com.cjqb.caijiqianbao.view.OrderEntry;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import java.util.List;

/**
 * 购买vip
 * wmy
 *
 * */
public class ShopVIPActivity extends BaseActivity implements PaymentResultListener {
    private TextView shop_vip_act_btn;
    private String may_quota;
    private List<Loan_info> loan_info;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_vip_activity);

        shop_vip_act_btn = findViewById(R.id.shop_vip_act_btn);
        mayQuota();
        shop_vip_act_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
    }


    private void mayQuota() {
        OkGo.<AppleBean>post(HttpUrl.MAY_QUOTA)

                .execute(new CommonCallback<AppleBean>() {
                    @Override
                    public AppleBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), AppleBean.class);
                    }

                    @Override
                    public void onSuccess(Response<AppleBean> response) {
                        super.onSuccess(response);
                        AppleBean body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
                                Option option = body.getOption();
                                may_quota = option.getMay_quota();
//                                String substring = option.getBank_card_number().substring(option.getBank_card_number().length() - 4, option.getBank_card_number().length());
//                                mTvBank.setText("尾号" + substring + "");
//                                mTvBank.setText(option.getBank_card_number() + "");
                                loan_info = body.getLoan_info();
                                if (loan_info == null) return;
//                                for (Loan_info loanInfo : loan_info) {
//                                    mTimeList.add(loanInfo.getLoan_time());
//                                    mMoney.put(loanInfo.getLoan_time(), loanInfo.getInterest());
//                                }
//                                mTvTime.setText(mTimeList.get(0));
//                                mTvGrossInterset.setText(mMoney.get(mTimeList.get(0)));
//                                amount = loan_info.get(0).getAmount();
//                                mTvPopuAmount.setText(loan_info.get(0).getAmount() + "元");
//                                mCbAgree.setText("我已阅读并同意购买" + loan_info.get(0).getAmount() + "元" + "VIP");
//                                mTvAgreeMoney.setText("购买VIP" + loan_info.get(0).getAmount() + "元");


                            }
                        }

                    }
                });

    }

    private void setData() {



        OkGo.<OrderEntry>post(HttpUrl.CREATEORDER)
                .params("collection_account",  SpUtil.getInstance().getStringValue(SpUtil.BANK_CARD_NUMBER))
                .params("may_quota", may_quota)
                .params("loan_time", loan_info.get(0).getCreate_time())
                .params("gross_interset", loan_info.get(0).getInterest())
                .params("loan_purpose", "买车")
                .params("amount", 200000)

                .execute(new CommonCallback<OrderEntry>() {
                    @Override
                    public OrderEntry convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), OrderEntry.class);
                    }

                    @Override
                    public void onSuccess(Response<OrderEntry> response) {
                        super.onSuccess(response);
                        OrderEntry body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
//                                SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");
//                                MainActivity.forward(AppleActivityStepEnd.this);
                                OrderEntry.OrderItemEntry orderItemEntry = body.getOrderData();
                                startPayment(orderItemEntry.getOrderNumber());
//                                finish();
                            } else if (("0099").equals(body.getCode())) {//获取验证码

                            }
                        }

                    }
                });
    }



    public void startPayment(String orderId) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_VKD76XDXkPW0UY");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.icon_login_ff);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            org.json.JSONObject options = new org.json.JSONObject();

            options.put("data-name", "Leding Hub");
            options.put("data-key", "FRme9fm6S9vxY1");
//            options.put("data-description", "Reference No. #123456");
//            options.put("data-image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("data-order_id", orderId);//from response of step 3.
//            options.put("theme.color", "#3399cc");
            options.put("data-currency", "INR");
            options.put("data-amount", "50000");//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","+919977665544");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("show", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        ShopVIPActivity.this.finish();
        Log.e("show" , s  + "成功");
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("show" , s  + "失败");
    }

}
