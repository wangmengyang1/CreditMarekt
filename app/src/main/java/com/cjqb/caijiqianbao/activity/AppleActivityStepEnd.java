package com.cjqb.caijiqianbao.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cjqb.caijiqianbao.AlarmReceiver;
import com.cjqb.caijiqianbao.Constants;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.stepEnd.AppleBean;
import com.cjqb.caijiqianbao.bean.stepEnd.Loan_info;
import com.cjqb.caijiqianbao.bean.stepEnd.Option;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.DialogUtil;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppleActivityStepEnd extends BaseActivity implements View.OnClickListener, PaymentResultListener {

    private TextView mTvTime;
    private TextView mTvLoanPur;
    private TextView mTvGrossInterset;
    private TextView mTvBank;
    String may_quota;
    double gross_interset;
    double interestDay = 0.04 / 100;
    private String collection_account;
    private TextView mTvAmount;
    private TextView mTvPopuAmount;
    private View mLL;
    private View mPopuView;
    private PopupWindow popupWindow;
    private List<Loan_info> loan_info;
    private String amount;
    private String smsSendNo;
    private Dialog dialog;
    private EditText mEtCode;
    private TextView tv_next;
    private TextView tv_name_1;
    private TextView tv_name_2;
    private TextView tv_name_3;
    private View rl_next;

    public static void forward(Context context, String bank_card_number) {
        Intent intent = new Intent(context, AppleActivityStepEnd.class);
//        intent.putExtra(Constants.PRODUCT_ID, product_id);
        intent.putExtra(Constants.BANK_NUM, bank_card_number);
        context.startActivity(intent);
    }

    private List<String> mList = new ArrayList<String>();
    private List<String> mTimeList = new ArrayList<String>();
    private Map<String, String> mMoney = new HashMap<String, String>();
    private CheckBox mCbAgree;
    private TextView mTvAgreeMoney;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_4);
        Checkout.clearUserData(getApplicationContext());
        mLL = findViewById(R.id.ll);
        findViewById(R.id.ll_time).setOnClickListener(this);
        findViewById(R.id.ll_loan_purpose).setOnClickListener(this);
        mTvTime = findViewById(R.id.tv_time);
        mTvLoanPur = findViewById(R.id.tv_loan_purpose);
        mTvGrossInterset = findViewById(R.id.tv_gross_interset);
        mTvBank = findViewById(R.id.tv_bank);
        mTvAmount = findViewById(R.id.tv_amount);
        mCbAgree = findViewById(R.id.cb_agree);
        rl_next = findViewById(R.id.rl_next);
        mTvAgreeMoney = findViewById(R.id.tv_agree_money);
        may_quota = SpUtil.getInstance().getStringValue(SpUtil.MAY_QUOTA);
//        gross_interset = Integer.parseInt(may_quota) * interestDay * 3;
//        gross_interset = Integer.parseInt(may_quota) * interestDay * 365;
//        mTvGrossInterset.setText(gross_interset + "");
        collection_account = getIntent().getStringExtra(Constants.BANK_NUM);

        mTvAmount.setText(may_quota);

        mTvLoanPur.setText("买车");
        mList.add("买车");
        mList.add("装修");
        mList.add("购买设备");
        mList.add("经营需要");
        mList.add("旅游");
        mList.add("购买耐用品");
        mList.add("个人日常消费");

//        mTimeList.add("3个月");
//        mTimeList.add("6个月");
//        mTimeList.add("9个月");
//        mTimeList.add("12个月");
//        mTimeList.add("24个月");

        mTvPopuAmount = findViewById(R.id.tv_popu_amount);
        mPopuView = getLayoutInflater().inflate(R.layout.popu_step_end, null);
//        mTvPopuAmount = mPopuView.findViewById(R.id.tv_popu_amount);
        popupWindow = DialogUtil.showPopuWindow(mLL, mPopuView);
        mPopuView.findViewById(R.id.img_closs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        mPopuView.findViewById(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setData();
            }
        });
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getData();

        tv_next = findViewById(R.id.tv_next);
        tv_name_1 = findViewById(R.id.tv_name_1);
        tv_name_2 = findViewById(R.id.tv_name_2);
        tv_name_3 = findViewById(R.id.tv_name_3);
        if (SpUtil.getInstance().getStringValue(SpUtil.VEST_VALUE).equals("0")) {
//            findViewById(R.id.img_1).setVisibility(View.GONE);
//            findViewById(R.id.img_is_v1).setVisibility(View.GONE);
//            findViewById(R.id.img_1).setVisibility(View.VISIBLE);
            findViewById(R.id.rl).setBackgroundResource(R.mipmap.ic_vip_new_bg);
            findViewById(R.id.rl_1).setVisibility(View.VISIBLE);
//            findViewById(R.id.tv_2).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_1).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_1).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_2).setVisibility(View.VISIBLE);
            tv_next.setText("下一步");
            tv_name_1.setText("借多少");
            tv_name_2.setText("收款账户");
            tv_name_3.setText("借款用途");
        }
    }

    private void getData() {
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
                                String may_quota = option.getMay_quota();
                                mTvAmount.setText(may_quota);
                                String substring = option.getBank_card_number().substring(option.getBank_card_number().length() - 4, option.getBank_card_number().length());
                                mTvBank.setText("尾号" + substring + "");
//                                mTvBank.setText(option.getBank_card_number() + "");
                                loan_info = body.getLoan_info();
                                if (loan_info == null) return;
                                for (Loan_info loanInfo : loan_info) {
                                    mTimeList.add(loanInfo.getLoan_time());
                                    mMoney.put(loanInfo.getLoan_time(), loanInfo.getInterest());
                                }
                                mTvTime.setText(mTimeList.get(0));
                                mTvGrossInterset.setText(mMoney.get(mTimeList.get(0)));
                                amount = loan_info.get(0).getAmount();
                                mTvPopuAmount.setText(loan_info.get(0).getAmount() + "元");
//                                mCbAgree.setText("我已阅读并同意购买" + loan_info.get(0).getAmount() + "元" + "VIP");
                                mTvAgreeMoney.setText("购买VIP" + loan_info.get(0).getAmount() + "元");

                            }
                        }

                    }
                });

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_loan_purpose) {
            showPickerView(mList);
        }
        if (i == R.id.ll_time) {
            showPickerView(mTimeList, mTvTime);
        }
    }

    private void setData() {
        if (!mCbAgree.isChecked()) {
            ToastUtil.show("请勾选购买VIP协议");
            return;
        }
        String loan_time = mTvTime.getText().toString();
        String loan_purpose = mTvLoanPur.getText().toString();
//        String bank_mobile = mEdPhone.getText().toString();
//        String code = mEdVcode.getText().toString();
//        if (TextUtils.isEmpty(name)) {
//            mEdName.setError("请输入姓名");
//            mEdName.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(bank_card_number)) {
//            mEdBank.setError("请输入银行卡号");
//            mEdBank.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(bank_mobile)) {
//            mEdPhone.setError("请输入预留手机号");
//            mEdPhone.requestFocus();
//            return;
//        }
//        if (!ValidatePhoneUtil.validateMobileNumber(bank_mobile)) {
//            mEdPhone.setError("请输入预留手机号");
//            mEdPhone.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(code)) {
//            mEdVcode.setError("请输入验证码");
//            mEdVcode.requestFocus();
//            return;
//        }

//        collection_account //收款账户
//                may_quota //借款金额
//                loan_time = //借款时长
//                        gross_interset = //总利息
//                                loan_purpose = //借款用途


        OkGo.<JsonBean>post(HttpUrl.CREATEORDER)
                .params("collection_account", collection_account)
                .params("may_quota", may_quota)
                .params("loan_time", loan_time)
                .params("gross_interset", gross_interset)
                .params("loan_purpose", loan_purpose)
                .params("amount", amount)

                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        JsonBean body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
//                                AppleActivityStepEnd.forward(AppleActivityStepThree.this);

//                                if (!TextUtils.isEmpty(body.getFailMsg()))ToastUtil.show(body.getFailMsg());
                                SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");
                                MainActivity.forward(AppleActivityStepEnd.this);
                                finish();
                            } else if (("0099").equals(body.getCode())) {//获取验证码
                                JSONObject jsonObject = JSON.parseObject(body.getData().toString());
                                smsSendNo = jsonObject.getString("smsSendNo");

                                showVcodeDialog();
                            }
                        }

                    }
                });
    }

    public void showVcodeDialog() {
        dialog = DialogUtil.commonDialog(this, R.layout.dialog_vcode);
        mEtCode = dialog.findViewById(R.id.et_code);
        dialog.setCancelable(false);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (ScreenDimenUtil.getInstance().getScreenWdith()) - DpUtil.dp2px(this, 100);
        dialog.getWindow().setAttributes(p);
        dialog.show();

    }

    public void submit(View v) {
        String smsVerifyCode = mEtCode.getText().toString();
        if (TextUtils.isEmpty(smsVerifyCode)) {
            ToastUtil.show("请输入验证码");
            return;
        }
        if (dialog == null) return;
        dialog.cancel();
        dialog.dismiss();
//        PracticeActivity.forward(this);
        paymentConfirmation(smsVerifyCode);
    }

    public void cancel(View v) {
        if (dialog == null) return;
        dialog.cancel();
        dialog.dismiss();
    }

    public void paymentConfirmation(String smsVerifyCode) {
        OkGo.<JsonBean>post(HttpUrl.PAYMENT_CONFIRMATION)
                .params("smsVerifyCode", smsVerifyCode)
                .params("smsSendNo", smsSendNo)

                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        JsonBean body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
//                                AppleActivityStepEnd.forward(AppleActivityStepThree.this);
                                if (!TextUtils.isEmpty(body.getFailMsg()))
                                    ToastUtil.show(body.getFailMsg());
                                if (("1003").equals(body.getFailCode())) {
//                                JSONObject jsonObject = JSON.parseObject(body.getData().toString());
//                                smsSendNo = jsonObject.getString("smsSendNo");
//
//                                showVcodeDialog();
//                                    int intValue = SpUtil.getInstance().getIntValue(SpUtil.COUNT_DIALOG);
                                    //获取AlarmManager实例
//                                    AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//                                    int anHour = 10 * 1000;  // 300秒
////                                    int anHour = 120 * 1000;  // 300秒
//                                    long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
//                                    Intent intent = new Intent(AppleActivityStepEnd.this, AlarmReceiver.class);
//                                    intent.setAction("alarm_intent_action");
//                                    PendingIntent pi = PendingIntent.getBroadcast(AppleActivityStepEnd.this, 0, intent, 0);
//                                    //开启提醒
//                                    manager.set(AlarmManager.RTC_WAKEUP, triggerAtTime, pi);
                                    if (SpUtil.getInstance().getBooleanValue(SpUtil.SHOW_DIALOG)){
                                        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 0);
                                    }else {
                                        setTime();
                                        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 1);
                                    }

                                    SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");
//                                    MainActivity.forward(AppleActivityStepEnd.this);
                                    finish();
                                }
                            }
                            // 8020 重新输入验证码
                        }

                    }
                });
    }

    public void gotoNext(View v) {
//        setData();
//       if (SpUtil.getInstance().getStringValue(SpUtil.VEST_VALUE).equals("0")) {
//            popupWindow.showAtLocation(mLL, Gravity.BOTTOM, 0, 0);
//        }else{
//        setData();
        startPayment();
        rl_next.setVisibility(View.VISIBLE);

    }
    public void next(View v) {
        setTime();
        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 1);
        SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");
        finish();
//        AppleActivityStepThree.forward(AppleActivityStepTwo.this);
    }


    /**
     *
     */

    private void showPickerView(final List<String> optionsItems, final TextView tv) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                tv.setText(optionsItems.get(options1) + "");
//                if (optionsItems.get(options1).contains("个月")) {
//                    String mon = optionsItems.get(options1).replace("个月", "");
//                    int i = Integer.parseInt(mon);
//                    gross_interset = Integer.parseInt(may_quota) * interestDay * 365 * i / 12;
//                    mTvGrossInterset.setText(gross_interset + "");
//                }
                mTvTime.setText(mTimeList.get(options1));
//                mTvTime.setText(mTimeList.get(options1));
                mTvGrossInterset.setText(mMoney.get(mTimeList.get(options1)));
//                mTvPopuAmount.setText();
                amount = loan_info.get(options1).getAmount();
                mTvPopuAmount.setText(loan_info.get(options1).getAmount() + "元");
//                mCbAgree.setText("我已阅读并同意购买" + loan_info.get(options1).getAmount() + "元" + "VIP");
                mTvAgreeMoney.setText("购买VIP" + loan_info.get(options1).getAmount() + "元");
            }
        })

//                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(optionsItems);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    private void showPickerView(final List<String> optionsItems) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                tv.setText(optionsItems.get(options1) + "");
//                if (optionsItems.get(options1).contains("个月")) {
//                    String mon = optionsItems.get(options1).replace("个月", "");
//                    int i = Integer.parseInt(mon);
//                    gross_interset = Integer.parseInt(may_quota) * interestDay * 365 * i / 12;
//                    mTvGrossInterset.setText(gross_interset + "");
//                }
                mTvLoanPur.setText(optionsItems.get(options1));
            }
        })

//                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(optionsItems);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    public void tip(View v) {

        WebViewActivity.forward(this, HttpUrl.vip_agreement, "协议");
    }





    public void startPayment() {
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
            options.put("data-description", "Reference No. #123456");
            options.put("data-image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("data-order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("data-currency", "INR");
            options.put("data-amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","+919977665544");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("show", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Log.e("show" , s  + "成功");
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("show" , s  + "失败");
    }

}
