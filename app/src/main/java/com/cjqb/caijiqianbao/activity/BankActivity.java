package com.cjqb.caijiqianbao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.BankInfoBean;
import com.cjqb.caijiqianbao.bean.bankBean.BankJsonBean;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BankActivity extends BaseActivity {

    private TextView titleView;
    private ImageView btnBack;
    private TextView bankName;
    private EditText accountNumber;
    private EditText ifscCode;
    private TextView submitBtn;
    String bank_code;
    List<BankInfoBean> mBankInfoList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_activity);
        initView();
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.titleView);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        bankName = (TextView) findViewById(R.id.bank_name);
        accountNumber = (EditText) findViewById(R.id.account_number);
        ifscCode = (EditText) findViewById(R.id.ifsc_code);
        submitBtn = (TextView) findViewById(R.id.submit_btn);
        getOrderBank();
        bankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        ArrayList<String> strings = new ArrayList<>();
                        for (BankInfoBean data : mBankInfoList) {
                            strings.add(data.getName());
                        }
                        showPickerView(strings, bankName);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bankName.equals("Bank Name")){
                    getSubmit();
                }else {
                    ToastUtil.show("Bank Name is null!");
                }

            }
        });

    }


    private void getSubmit(){
        OkGo.<JsonBean>post(HttpUrl.BINDCARD)
//                .params("name", name)
//                .params("id_number", SpUtil.getInstance().getStringValue(SpUtil.ID_NUMBER))
                .params("bank_card_number", accountNumber.getText().toString())
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
//                                SpUtil.getInstance().setStringValue(SpUtil.BANK_MOBILE, bank_mobile);
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "OrderInfo");
//                                AppleActivityStepEnd.forward(AppleActivityStepThree.this, bank_card_number);
//                                finish();
//                                nextData();
                            }
                        }

                    }
                });
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
}
