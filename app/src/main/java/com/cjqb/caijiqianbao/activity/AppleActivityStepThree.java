package com.cjqb.caijiqianbao.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.BankInfoBean;
import com.cjqb.caijiqianbao.bean.BindCodeBean;
import com.cjqb.caijiqianbao.bean.bankBean.BankJsonBean;
import com.cjqb.caijiqianbao.bean.stepEnd.AppleBean;
import com.cjqb.caijiqianbao.bean.stepEnd.Loan_info;
import com.cjqb.caijiqianbao.bean.stepEnd.Option;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.ChineseCheck;
import com.cjqb.caijiqianbao.utils.DialogUtil;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.utils.L;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.cjqb.caijiqianbao.utils.ValidatePhoneUtil;
import com.cjqb.caijiqianbao.utils.WordUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AppleActivityStepThree extends BaseActivity {

    private String bank_code;
    private String smsSendNo;
    private String merOrderId;
    private EditText mEdIdNumber;
    private Dialog dialog;
    private EditText mEtCode;
    private String bank_card_number;
    private List<Loan_info> loan_info;
    private String amount;
    private String may_quota;
    private boolean booleanValue;
    private TextView tv_v_amount;
    private int count = 0;
    private TextView tv_next;

    public static void forward(Context context) {
        Intent intent = new Intent(context, AppleActivityStepThree.class);
//        intent.putExtra(Constants.PRODUCT_ID, product_id);
//        intent.putExtra(Constants.TO_NAME, title);
        context.startActivity(intent);
    }

    private EditText mEdBank;
    private EditText mEdName;
    private EditText mEdPhone;
    private EditText mEdVcode;
    private Button mBtCode;
    private int mCount = 60;
    private String mGetCodeAgain = WordUtil.getString(R.string.reg_get_code_again);
    ;
    private Handler handle;
    private ImageView img;
    private View tv;
    private TextView mBankType;
    List<BankInfoBean> mBankInfoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_3);

        mEdBank = findViewById(R.id.et_bank);
//        mEdName = findViewById(R.id.et_name);
//        mEdName.setText(SpUtil.getInstance().getStringValue(SpUtil.NAME));

        mEdPhone = findViewById(R.id.et_phone);
        mEdVcode = findViewById(R.id.et_vcode);
        tv_next = findViewById(R.id.tv_next);
        tv_v_amount = findViewById(R.id.tv_v_amount);
//        mEdIdNumber = findViewById(R.id.et_id_number);
//        mEdIdNumber.setText(SpUtil.getInstance().getStringValue(SpUtil.ID_NUMBER));
        mBankType = findViewById(R.id.tv_bank_type);
        mBtCode = findViewById(R.id.bt_code);
        mBtCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        mayQuota();

        img = findViewById(R.id.img);
        tv = findViewById(R.id.tv);
        String stringValue = SpUtil.getInstance().getStringValue(SpUtil.VEST_TEMP);
        if ("1".equals(stringValue)) {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_red_step_3));
//            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_step_3));
            tv.setBackgroundResource(R.mipmap.ic_item_head);
        } else {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_blue_step_3));
//            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_step_new_3));
            tv.setBackgroundResource(R.mipmap.ic_item_new_head);
        }
        img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_red_step_3));
        setTitle("基本信息");

        mBankType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                for (BankInfoBean data : mBankInfoList) {
                    strings.add(data.getName());
                }
                showPickerView(strings, mBankType);
            }
        });
        getOrderBank();
        booleanValue = SpUtil.getInstance().getBooleanValue(SpUtil.SHOW_DIALOG);
        if (booleanValue) {
            img.setImageResource(R.mipmap.ic_new_vip_tip_bg);
            tv_next.setText("马上领钱");
            findViewById(R.id.ll_v_amount).setVisibility(View.VISIBLE);
            findViewById(R.id.view_v_amount).setVisibility(View.VISIBLE);
        }

    }

    private void getOrderBank() {
        OkGo.<BankJsonBean>post(HttpUrl.GETORDERBANKINFO)
                .execute(new CommonCallback<BankJsonBean>() {
                    @Override
                    public BankJsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), BankJsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<BankJsonBean> response) {
                        super.onSuccess(response);
                        BankJsonBean body = response.body();
                        if (body != null) {
                            if (("200").equals(body.getCode())) {
                                mBankInfoList = body.getData();
                            }
                        }

                    }
                });
//        OkGo.<JsonBean>post(HttpUrl.GETORDERBANKINFO)
//                .execute(new CommonCallback<JsonBean>() {
//                    @Override
//                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
//                        return JSON.parseObject(response.body().string(), JsonBean.class);
//                    }
//
//                    @Override
//                    public void onSuccess(Response<JsonBean> response) {
//                        super.onSuccess(response);
//                        JsonBean body = response.body();
//                        if (body != null) {
//                            if (("200").equals(body.getCode())) {
//                                JSONObject data = (JSONObject) body.getData();
////                                JSONObject extend_info = .getJSONObject("extend_info");
//                                if (null != data) {
//                                    mBankInfoList = new ArrayList<>();
//                                    Set<String> keySet = data.keySet();
//                                    for (String s : keySet) {
//                                        BankInfoBean bankInfoBean = new BankInfoBean();
//                                        String value = data.getString(s);
//                                        bankInfoBean.setName(s);
//                                        bankInfoBean.setCode(value);
//                                        mBankInfoList.add(bankInfoBean);
//                                    }
//                                }
//                            }
//                        }
//
//                    }
//                });
    }


    private void setData() {
//        String name = mEdName.getText().toString();
        final String bank_card_number = mEdBank.getText().toString();
        final String bank_mobile = mEdPhone.getText().toString();
        String code = mEdVcode.getText().toString();

//        if (TextUtils.isEmpty(name)) {
//            mEdName.setError("请输入姓名");
//            mEdName.requestFocus();
//            return;
//        }
//        if (!ChineseCheck.checkNameChese(name)) {
//            ToastUtil.show("请输入真实姓名");
//            return;
//        }
        if (TextUtils.isEmpty(bank_card_number)) {
            mEdBank.setError("请输入银行卡号");
            mEdBank.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(bank_mobile)) {
            mEdPhone.setError("请输入预留手机号");
            mEdPhone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            mEdVcode.setError("请输入验证码");
            mEdVcode.requestFocus();
            return;
        }

//                name //持卡人
//        bank_card_number //银行卡号
//                bank_mobile //预留手机号
//        code //验证码
        OkGo.<JsonBean>post(HttpUrl.BINDCARD)
//                .params("name", name)
//                .params("id_number", SpUtil.getInstance().getStringValue(SpUtil.ID_NUMBER))
                .params("bank_card_number", bank_card_number)
                .params("bank_mobile", bank_mobile)


                .params("smsVerifyCode", code)
                .params("smsSendNo", smsSendNo)
                .params("merOrderId", merOrderId)
//                .params("code", code)

//                .params("bank_mobile", "15057145951")
//                .params("code", 1111)

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
                                SpUtil.getInstance().setStringValue(SpUtil.BANK_MOBILE, bank_mobile);
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "OrderInfo");
//                                AppleActivityStepEnd.forward(AppleActivityStepThree.this, bank_card_number);
//                                finish();
                                nextData();
                            }
                        }

                    }
                });
    }

    private void getCode() {
        final String phoneNum = mEdPhone.getText().toString().trim();
        String mBankTypeName = mBankType.getText().toString();
//        String name = mEdName.getText().toString();
//        String id_number = mEdIdNumber.getText().toString();

//        if (TextUtils.isEmpty(name)) {
//            mEdName.setError("请输入姓名");
//            mEdName.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(id_number)) {
//            mEdName.setError("请输入身份证");
//            mEdName.requestFocus();
//            return;
//        }
        if (TextUtils.isEmpty(mBankTypeName)) {
            ToastUtil.show("请选择银行");
            return;
        }
        if (TextUtils.isEmpty(phoneNum)) {
            mEdPhone.setError(WordUtil.getString(R.string.reg_input_phone));
            mEdPhone.requestFocus();
            return;
        }
        if (!ValidatePhoneUtil.validateMobileNumber(phoneNum)) {
            mEdPhone.setError(WordUtil.getString(R.string.login_phone_error));
            mEdPhone.requestFocus();
            return;
        }
        bank_card_number = mEdBank.getText().toString();
        if (TextUtils.isEmpty(bank_card_number)) {
            mEdBank.setError("请输入银行卡号");
            mEdBank.requestFocus();
            return;
        }

        OkGo.<BindCodeBean>post(HttpUrl.SENDBINDCARDVERIFYCODE)
                .params("name", SpUtil.getInstance().getStringValue(SpUtil.NAME))
                .params("id_number", SpUtil.getInstance().getStringValue(SpUtil.ID_NUMBER))
                .params("bank_mobile", phoneNum)
                .params("bank_name", mBankTypeName)
                .params("bank_code", bank_code)
                .params("bank_card_number", bank_card_number)
                .execute(new CommonCallback<BindCodeBean>() {
                    @Override
                    public void onSuccess(Response<BindCodeBean> response) {
                        super.onSuccess(response);
                        BindCodeBean bean = response.body();

                        if ("200".equals(bean.getCode())) {
//                        if ("T".equals(bean.getData().getRetFlag())){

                            if ("210".equals(bean.getFailCode())) {
                                SpUtil.getInstance().setStringValue(SpUtil.BANK_MOBILE, phoneNum);
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "OrderInfo");
//                                AppleActivityStepEnd.forward(AppleActivityStepThree.this, bank_card_number);
//                                finish();
                                nextData();
                                return;
                            }
                            smsSendNo = bean.getData().getSmsSendNo();
                            merOrderId = bean.getData().getMerOrderId();
                            mCount = 60;
                            mBtCode.setText(mCount-- + "s后" + mGetCodeAgain);
                            mBtCode.setEnabled(false);
                            changeCodeText();
                        } else {
                            ToastUtil.show(bean.getData().getResultMsg());
                        }
//                        if ("01".equals(bean.getData().getSmsStatus())) {
//                            mCount = 60;
//                            mBtCode.setText(mCount-- + "s后" + mGetCodeAgain);
//                            mBtCode.setEnabled(false);
//                            changeCodeText();
//                        } else {
//                            ToastUtil.show(bean.getMsg());
//                        }
                    }

                    @Override
                    public BindCodeBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), BindCodeBean.class);
                    }
                });
    }

    /**
     * 修改文字
     */
    private void changeCodeText() {
        if (handle == null) handle = new Handler();
        handle.postDelayed(runnable, 1000);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mCount != 0) {
                mBtCode.setText(mCount-- + "s后" + mGetCodeAgain);
                changeCodeText();
            } else {
                mBtCode.setText(mGetCodeAgain);
                mBtCode.setEnabled(true);
                handle.removeCallbacks(runnable);
            }

        }
    };

    public void gotoNext(View v) {
        setData();
//        AppleActivityStepEnd.forward(AppleActivityStepThree.this, "123123123");
    }

    /**
     *
     */
    private void showPickerView(final List<String> optionsItems, final TextView tv) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv.setText(mBankInfoList.get(options1).getName());
                bank_code = mBankInfoList.get(options1).getCode();

            }
        })

//                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

//        ToastUtil.show("请绑定近期有交易记录的银行卡\n" +
//                "否则严重影响通过率");

        pvOptions.setPicker(optionsItems);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
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
                                amount = loan_info.get(0).getAmount();
                                tv_v_amount.setText(amount);
//                                mTvPopuAmount.setText(loan_info.get(0).getAmount() + "元");
//                                mCbAgree.setText("我已阅读并同意购买" + loan_info.get(0).getAmount() + "元" + "VIP");
//                                mTvAgreeMoney.setText("购买VIP" + loan_info.get(0).getAmount() + "元");


                            }
                        }

                    }
                });

    }

    private void nextData() {
//        String loan_time = mTvTime.getText().toString();
//        String loan_purpose = mTvLoanPur.getText().toString();
//        collection_account //收款账户
//                may_quota //借款金额
//                loan_time = //借款时长
//                        gross_interset = //总利息
//                                loan_purpose = //借款用途


        OkGo.<JsonBean>post(HttpUrl.CREATEORDER)
                .params("collection_account", bank_card_number)
                .params("may_quota", may_quota)
                .params("loan_time", loan_info.get(0).getLoan_time())
                .params("gross_interset", loan_info.get(0).getInterest())
                .params("loan_purpose", "买车")
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
//                                MainActivity.forward(AppleActivityStepThree.this);
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
                        if (count == 2) {
                            SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");
                            finish();
                        }
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
//                                    if (SpUtil.getInstance().getBooleanValue(SpUtil.SHOW_DIALOG)) {
//                                        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 0);
//                                    } else {
//                                        setTime();
//                                        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 1);
//                                    }

//                                    SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");

                                    if (booleanValue) {
                                        count = 2;
                                        ToastUtil.show("交易失败，请更换银行卡支付");
                                        mEdBank.setText("");
//                                        mEdName.setVisibility(View.GONE);
                                        mEdPhone.setText("");
                                        mEdVcode.setText("");
                                        mBankType.setText("");
                                        return;
                                    }
                                    AppleActivityStepEnd.forward(AppleActivityStepThree.this, bank_card_number);
                                    finish();
                                    return;
                                }
                                SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "1");
                                finish();
                            }
                            // 8020 重新输入验证码
                        }

                    }
                });
    }


}
