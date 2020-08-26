package com.cjqb.caijiqianbao.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.BankInfoBean;
import com.cjqb.caijiqianbao.bean.LoginEntry;
import com.cjqb.caijiqianbao.bean.bankBean.BankJsonBean;
import com.cjqb.caijiqianbao.bean.loginBean.loginJsonData;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpClient;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.cjqb.caijiqianbao.utils.ValidatePhoneUtil;
import com.cjqb.caijiqianbao.utils.WordUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private EditText mEditPhone;
    private EditText mEditCode;
    private Button  mBtLogin;
    private TextView mBtCode;
    private TextView mTvTip;
    private int mCount = 60;
    private CheckBox mCbAgree;
    private String mGetCodeAgain;
    private Handler handle;
    private List<BankInfoBean> mBankInfoList;


    public static void forward(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Register");
        mEditPhone = findViewById(R.id.et_login_phone);
        mEditCode = findViewById(R.id.et_login_code);
        mBtCode = findViewById(R.id.bt_code);
        mBtLogin = findViewById(R.id.bt_login);
        mTvTip = findViewById(R.id.tv_tip);
        mCbAgree = findViewById(R.id.cb_agree);

        mGetCodeAgain = WordUtil.getString(R.string.reg_get_code_again);
        mTvTip.setOnClickListener(this);
        mBtCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        mBtLogin.setOnClickListener(this);
        mCbAgree.setOnClickListener(this);
        mEditPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length() == 11) {
                    mBtCode.setEnabled(true);
                } else {
                    mBtCode.setEnabled(false);
                }
                changeEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEditCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCount == 60) {
                    mBtCode.setEnabled(true);
                }
                changeEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 按钮是否可点击
     */
    private void changeEnable() {
        String phone = mEditPhone.getText().toString();
        String code = mEditCode.getText().toString();
        mBtLogin.setEnabled(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code) && mCbAgree.isChecked());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_login) {
            //TODO
            login();

        } else if (i == R.id.tv_tip) {
            WebViewActivity.forward(this, HttpUrl.USER_TIP,"协议");

        } else if (i == R.id.cb_agree) {
            changeEnable();

        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {

        Log.e("show" , "执行");
//        SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG,1);
//        AppleActivityStepTwo.forward(LoginActivity.this);
        String phoneNum = mEditPhone.getText().toString();
//        if (TextUtils.isEmpty(phoneNum)) {
//            mEditPhone.setError(WordUtil.getString(R.string.reg_input_phone));
//            mEditPhone.requestFocus();
//            return;
//        }
//        if (!ValidatePhoneUtil.validateMobileNumber(phoneNum)) {
//            mEditPhone.setError(WordUtil.getString(R.string.login_phone_error));
//            mEditPhone.requestFocus();
//            return;
//        }
//        mEditCode.requestFocus();
        String deviceID = Settings.System.getString(this.getContentResolver(), Settings.System.ANDROID_ID);
        OkGo.<JsonBean>post(HttpUrl.SEND)
                .params("mobile", "91" + phoneNum)
                .params("imei", deviceID)
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        JsonBean bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                mCount = 60;
                                mBtCode.setText(mCount-- + "s后" + mGetCodeAgain);
                                mBtCode.setEnabled(false);
                                changeCodeText();
                            } else if (("90000").equals(bean.getCode())) {
                                ToastUtil.show(response.body().getMsg());
                            }
                        }
                    }

                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handle != null)
            handle.removeCallbacks(runnable);
    }

    /**
     * 注册并登陆
     */
    private void login() {
        final String phoneNum = mEditPhone.getText().toString();


        String code = mEditCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            mEditCode.setError(WordUtil.getString(R.string.reg_input_code));
            mEditCode.requestFocus();
            return;
        }
        OkGo.<loginJsonData>post(HttpUrl.LOGIN)
                .params("mobile", "91" + phoneNum)
                .params("code", code)
                .execute(new CommonCallback<loginJsonData>() {
                    @Override
                    public void onSuccess(Response<loginJsonData> response) {
                        super.onSuccess(response);
                        loginJsonData bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                Map<String, String> map = new HashMap<>();
                                map.put(SpUtil.UID, bean.getData().getUid());
                                map.put(SpUtil.TOKEN, bean.getData().getToken());
                                SpUtil.getInstance().setMultiStringValue(map);
                                HttpClient.getInstance().init(getApplication());
                                SpUtil.getInstance().setBooleanValue(SpUtil.IS_LOGIN, true);
                                SpUtil.getInstance().setStringValue(SpUtil.PHONE, phoneNum);
                                ToastUtil.show("登录成功");
                                JSONObject user_info = bean.getData().getUser_info();
                                String name = user_info.getString("name");
                                String id_number = user_info.getString("id_number");
                                String bank_mobile = user_info.getString("bank_mobile");
                                String bank_card_number = user_info.getString("bank_card_number");

                                SpUtil.getInstance().setStringValue(SpUtil.NAME, name);
                                SpUtil.getInstance().setStringValue(SpUtil.ID_NUMBER, id_number);
                                SpUtil.getInstance().setStringValue(SpUtil.BANK_MOBILE, bank_mobile);
                                SpUtil.getInstance().setStringValue(SpUtil.BANK_CARD_NUMBER, bank_card_number);
//                                if (null != user_info) {
//                                    Map<String, Object> mapInfo = (Map<String, Object>) user_info;
//
//                                    SpUtil.getInstance().setMultiObjectValue(mapInfo);
//
//                                    //是否为会员1：是 0：否
//                                    if (1 == user_info.getInteger("is_vip")) {
//                                        MainActivity.forward(LoginActivity.this);
//                                        finish();
//                                        return;
//                                    }
//                                }
                                SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, user_info.getInteger("is_vip").toString());
                                if (1 == user_info.getInteger("is_vip")) {
//                                    MainActivity.forward(LoginActivity.this);
                                    finish();
                                    return;
                                }
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                String stage = bean.getData().getStage();
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, stage);
//                                if ("BasicInfo".equals(stage)) {
//                                    AppleActivityStepFirst.forward(LoginActivity.this);
//                                } else if ("ContactInfo".equals(stage)) {
//                                    AppleActivityStepTwo.forward(LoginActivity.this);
//                                } else if ("BankInfo".equals(stage)) {
//                                    AppleActivityStepThree.forward(LoginActivity.this);
//                                }else if ("OrderInfo".equals(stage)) {
//                                    AppleActivityStepEnd.forward(LoginActivity.this,bank_card_number);
//                                }
//                                finish();
                                getverifystatus();
                            } else if (("90000").equals(bean.getCode())) {
//                                ToastUtil.show(bean.getMsg());
                            }
                        }
                    }

                    @Override
                    public loginJsonData convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), loginJsonData.class);
                    }
                });
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





//                                if ("BasicInfo".equals(stage)) {
//                                    AppleActivityStepFirst.forward(LoginActivity.this);
////                                } else if ("ContactInfo".equals(stage)) {
//                                    AppleActivityStepTwo.forward(LoginActivity.this);
//                                } else if ("BankInfo".equals(stage)) {
                                    AppleActivityStepThree.forward(LoginActivity.this);
////                                } else if ("OrderInfo".equals(stage)) {
//                                    AppleActivityStepEnd.forward(LoginActivity.this, SpUtil.getInstance().getStringValue(SpUtil.BANK_CARD_NUMBER));
//                                }

                                Intent intent = new Intent(LoginActivity.this , ProFileInformationActivity.class);
                                intent.putExtra("STAGE" , stage);
                                startActivity(intent);


                                LoginActivity.this.finish();
                            }
                        }

                    }
                });
    }
}
