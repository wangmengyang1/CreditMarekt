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
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本信息页面
 * wmy
 */
public class BasicInitActivity extends BaseActivity {

    private TextView titleView;
    private ImageView btnBack;
    private EditText userName;
    private EditText cardId;
    private TextView gender;
    private TextView maritalStatus;
    private TextView education;
    private EditText address;
    private EditText city;
    private TextView state;
    private EditText contactName;
    private EditText contactPhoneNumber;
    private TextView submitBtn;
    private TextView monthly_salary;

    private EditText email;

    private List<String> stateList = new ArrayList<String>();
    private List<String> mMoneyList = new ArrayList<String>();
    private List<String> mList = new ArrayList<String>();
    private List<String> mRelationList = new ArrayList<String>();
    private List<String> mIsStudyList = new ArrayList<String>();
    private List<String> mMarriageList = new ArrayList<String>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_init_activity);

        initView();
    }


//    @Subscribe (threadMode = ThreadMode.MAIN)
//    public void onThreadMain(){
//
//    }

    private void initView() {

        mMarriageList.add("未婚");
        mMarriageList.add("已婚");
        mMarriageList.add("离异");
        mMarriageList.add("丧偶");

        mIsStudyList.add("研究生及以上");
        mIsStudyList.add("大学本科");
        mIsStudyList.add("大专");
        mIsStudyList.add("高中及以下");

        mMoneyList.add("0-3500");
        mMoneyList.add("3500-5000");
        mMoneyList.add("5000-8000");
        mMoneyList.add("8000-10000");
        mMoneyList.add("10000+");

        stateList.add("中国");
        stateList.add("美国");
        stateList.add("发过");
        stateList.add("日本");
        stateList.add("韩国");

        mList.add("个体");
        mList.add("机关单位");
        mList.add("事业单位");
        mList.add("企业单位");
        mList.add("其他");
        email = findViewById(R.id.email);
        titleView = (TextView) findViewById(R.id.titleView);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        userName = (EditText) findViewById(R.id.user_name);
        cardId = (EditText) findViewById(R.id.card_id);
        gender = (TextView) findViewById(R.id.gender);
        maritalStatus = (TextView) findViewById(R.id.marital_status);
        education = (TextView) findViewById(R.id.education);
        address = (EditText) findViewById(R.id.address);
        city = (EditText) findViewById(R.id.city);
//        state = (TextView) findViewById(R.id.state);
//        contactName = (EditText) findViewById(R.id.contact_name);
//        contactPhoneNumber = (EditText) findViewById(R.id.contact_phone_number);
        submitBtn = (TextView) findViewById(R.id.submit_btn);
        monthly_salary = findViewById(R.id.monthly_salary);


        maritalStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView(mMarriageList, maritalStatus);
            }
        });

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView(mIsStudyList, education);
            }
        });

        monthly_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView(mMoneyList, monthly_salary);
            }
        });

//        state.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPickerView(stateList, state);
//            }
//        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView(mList, gender);
            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().isEmpty() ||
                        cardId.getText().toString().isEmpty() ||
                        gender.getText().toString().isEmpty() ||
                        maritalStatus.getText().toString().isEmpty() ||
                        education.getText().toString().isEmpty() ||
                        address.getText().toString().isEmpty() ||
                        city.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty()
//                        contactName.getText().toString().isEmpty()||
//                        contactPhoneNumber.getText().toString().isEmpty()
                ){
                    ToastUtil.show("Information cannot be empty!");
                }
                next();
                nextSecond();

            }
        });

    }


    //用户名，id
    public void next(){
        OkGo.<JsonBean>post(HttpUrl.AUTH_INFO)
                .params("name", userName.getText().toString())
                .params("id_number", cardId.getText().toString())
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
                                SpUtil.getInstance().setStringValue(SpUtil.NAME, userName.getText().toString());
                                SpUtil.getInstance().setStringValue(SpUtil.ID_NUMBER, cardId.getText().toString());
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "ContactInfo");
                                finish();
                            }
                        }

                    }
                });
    }

    public void nextSecond(){
        OkGo.<JsonBean>post(HttpUrl.BASIC_INFO)
//                .params("basic_network_time", basic_network_time)
//                .params("basic_security_fund", basic_security_fund)
                .params("basic_monthly_income", monthly_salary.getText().toString())
                .params("basic_school", education.getText().toString())
//
//                .params("urgent_name", "")
//                .params("urgent_phone", "")
//                .params("urgent_relation", "")
//
//                .params("other_name", "")
//                .params("other_phone", "")
//                .params("other_relation", "")
                .params("marriage", education.getText().toString())
                .params("emailID", email.getText().toString())
                .params("occupation", gender.getText().toString())
                .params("area", city.getText().toString() + address.getText().toString())
//                .params("basic_network_time", "1年一下")
//                .params("basic_security_fund", "有")
//                .params("basic_monthly_income", "3500")
//
//                .params("urgent_name", "xt")
//                .params("urgent_phone", "15057145951")
//                .params("urgent_relation", "1")
//
//                .params("other_name", "xt")
//                .params("other_phone", "15057145951")
//                .params("other_relation", "1")


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
//                                EventBus.getDefault().register(this);
//                                EventBus.getDefault().post(MessageEvent.BASIC_INIT);
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "ContactInfo");
                                finish();
                            }
                        }
                    }
                });
    }

    //
    private void showPickerView(final List<String> optionsItems, final TextView tv) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv.setText(optionsItems.get(options1) + "");
            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(optionsItems);//一级选择器
        pvOptions.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }



}
