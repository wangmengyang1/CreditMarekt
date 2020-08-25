package com.cjqb.caijiqianbao.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.cityBean.JsonCityBean;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.CityUtil;
import com.cjqb.caijiqianbao.utils.ProcessResultUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppleActivityStepTwo extends BaseActivity implements View.OnClickListener {

    //省
    private List<String> options1Items = new ArrayList<String>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    String[] mPremissions = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
    };
    private ProcessResultUtil mProcessResultUtil;
    static int isSetting = 1;
    private List<String> mTimeList = new ArrayList<String>();
    private List<String> mMoneyList = new ArrayList<String>();
    private List<String> mIsFundList = new ArrayList<String>();
    private List<String> mRelationList = new ArrayList<String>();
    private List<String> mList = new ArrayList<String>();
    private TextView mTvUrgentRelation;
    private TextView mTvUrgentPhone;
    private TextView mTvUrgentName;
    private TextView mTvTime;
    private TextView mTvMoney;
    private TextView mTvFund;
    private TextView mTvOtherName;
    private TextView mTvOtherRelation;
    private TextView mTvOtherPhone;

    private TextView mTvAddress;
    private TextView mEdOp;
    private ImageView img;
    private View tv;
    private TextView mTvStudyInfo;
    private List<String> mIsStudyList = new ArrayList<>();
    private List<String> mMarriageList = new ArrayList<>();
    private String basic_school;
    private TextView mTvMarriageInfo;

    public static void forward(Context context) {
        Intent intent = new Intent(context, AppleActivityStepTwo.class);
//        intent.putExtra(Constants.PRODUCT_ID, product_id);
//        intent.putExtra(Constants.TO_NAME, title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_2);
        mTvUrgentPhone = findViewById(R.id.tv_urgent_phone);
        mTvUrgentName = findViewById(R.id.tv_urgent_name);
        mTvUrgentRelation = findViewById(R.id.tv_urgent_relation);

        mTvOtherName = findViewById(R.id.tv_other_name);
        mTvOtherRelation = findViewById(R.id.tv_other_relation);
        mTvOtherPhone = findViewById(R.id.tv_other_phone);

        mTvTime = findViewById(R.id.tv_time);
        mTvMoney = findViewById(R.id.tv_money);
        mTvFund = findViewById(R.id.tv_security_fund);


        mTvOtherName.setOnClickListener(this);
        mTvOtherRelation.setOnClickListener(this);
        mTvOtherPhone.setOnClickListener(this);

        mTvUrgentPhone.setOnClickListener(this);
        mTvUrgentRelation.setOnClickListener(this);
        mTvUrgentName.setOnClickListener(this);

        mEdOp = findViewById(R.id.tv_op);
        mTvAddress = findViewById(R.id.tv_address);
        mTvStudyInfo = findViewById(R.id.tv_study_info);
        mTvMarriageInfo = findViewById(R.id.tv_marriage_info);

        mEdOp.setOnClickListener(this);
        mTvStudyInfo.setOnClickListener(this);
        mTvAddress.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mTvMoney.setOnClickListener(this);
        mTvFund.setOnClickListener(this);
        mTvMarriageInfo.setOnClickListener(this);
//        findViewById(R.id.ll_urgent_phone).setOnClickListener(this);
//        findViewById(R.id.ll_urgent_name).setOnClickListener(this);
        mProcessResultUtil = new ProcessResultUtil(this);
        mTimeList.add("1年以下");
        mTimeList.add("1年-2年");
        mTimeList.add("2年-3年");
        mTimeList.add("3年-4年");
        mTimeList.add("4年-5年");
        mTimeList.add("5年以上");

        mMoneyList.add("0-3500");
        mMoneyList.add("3500-5000");
        mMoneyList.add("5000-8000");
        mMoneyList.add("8000-10000");
        mMoneyList.add("10000+");

        mRelationList.add("亲属");
        mRelationList.add("同事");
        mRelationList.add("朋友");

        mIsFundList.add("有");
        mIsFundList.add("无");


        mList.add("个体");
        mList.add("机关单位");
        mList.add("事业单位");
        mList.add("企业单位");
        mList.add("其他");

//        高中及以下，大专，大学本科，研究生及以上
        mIsStudyList.add("研究生及以上");
        mIsStudyList.add("大学本科");
        mIsStudyList.add("大专");
        mIsStudyList.add("高中及以下");

        mMarriageList.add("未婚");
        mMarriageList.add("已婚");
        mMarriageList.add("离异");
        mMarriageList.add("丧偶");

        img = findViewById(R.id.img);
        tv = findViewById(R.id.tv);
        String stringValue = SpUtil.getInstance().getStringValue(SpUtil.VEST_TEMP);
        if ("1".equals(stringValue)) {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_red_step_2));
            tv.setBackgroundResource(R.mipmap.ic_item_head);
        } else {
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_blue_step_2));
            tv.setBackgroundResource(R.mipmap.ic_item_new_head);
        }
        img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_red_step_2));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_urgent_phone) {
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI), 0);
        }
        if (i == R.id.tv_urgent_name) {
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI), 0);
        }

        if (i == R.id.tv_other_phone) {
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI), 1);
        }
        if (i == R.id.tv_other_name) {
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI), 1);
        }
        if (i == R.id.tv_time) {
            showPickerView(mTimeList, mTvTime);
        }
        if (i == R.id.tv_money) {
            showPickerView(mMoneyList, mTvMoney);
        }
        if (i == R.id.tv_security_fund) {
            showPickerView(mIsFundList, mTvFund);
        }
        if (i == R.id.tv_study_info) {
            showPickerView(mIsStudyList, mTvStudyInfo);
        }
        if (i == R.id.tv_marriage_info) {
            showPickerView(mMarriageList, mTvMarriageInfo);
        }
        if (i == R.id.tv_urgent_relation) {
            showPickerView(mRelationList, mTvUrgentRelation);
        }
        if (i == R.id.tv_other_relation) {
            showPickerView(mRelationList, mTvOtherRelation);
        }
        if (i == R.id.tv_op) {
            showPickerView(mList, mEdOp);
        }
        if (i == R.id.tv_address) {
            parseData();
            showPickerViewAddress();
        }
    }

    /**
     * 展示选择器
     */
    private void showPickerViewAddress() {
        // 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1) +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                mTvAddress.setText(tx);
                mTvAddress.setTextColor(getResources().getColor(R.color.textColor));
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (isSetting == 1) {
//            isSetting = isSetting + 1;
//            check();
//        }
    }

//    public void check() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
////            showContacts();
////            showSms();
//        } else {
//            init();
//        }
//    }

//    private void init() {
//        mProcessResultUtil.requestPermissions(mPremissions, new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//    }

    private void setData() {
        String urgent_name = mTvUrgentRelation.getText().toString();
        String urgent_phone = mTvUrgentPhone.getText().toString();
        String urgent_relation = mTvUrgentName.getText().toString();

        String basic_network_time = mTvTime.getText().toString();
        String basic_monthly_income = mTvMoney.getText().toString();
        String basic_school = mTvStudyInfo.getText().toString();
        String basic_security_fund = mTvFund.getText().toString();

        String other_name = mTvOtherName.getText().toString();
        String other_phone = mTvOtherRelation.getText().toString();
        String other_relation = mTvOtherPhone.getText().toString();
        String marriage = mTvMarriageInfo.getText().toString();

        String occupation = mEdOp.getText().toString();
        String area = mTvAddress.getText().toString();
        if (TextUtils.isEmpty(basic_school)) {
            ToastUtil.show("请选择学历");
            return;
        }
        if (TextUtils.isEmpty(marriage)) {
            ToastUtil.show("请选择婚姻状态");
            return;
        }
        if (TextUtils.isEmpty(area)) {
            ToastUtil.show("请选择所在地");
            return;
        }
        if (TextUtils.isEmpty(occupation)) {
            ToastUtil.show("请输入您的职业");
            return;
        }

//        if (TextUtils.isEmpty(basic_network_time)) {
//            ToastUtil.show("请选择入网时长");
//            return;
//        }
//        if (TextUtils.isEmpty(basic_security_fund)) {
//            ToastUtil.show("请选择社保公积金");
//            return;
//        }

        if (TextUtils.isEmpty(basic_monthly_income)) {
            ToastUtil.show("请选择月收入");
            return;
        }

//        if (TextUtils.isEmpty(urgent_name)) {
//            ToastUtil.show("请选择紧急联系人");
//            return;
//        }
//        if (TextUtils.isEmpty(urgent_phone)) {
//            ToastUtil.show("请选择紧急联系人");
//            return;
//        }
//        if (TextUtils.isEmpty(urgent_relation)) {
//            ToastUtil.show("请选择紧急联系人关系");
//            return;
//        }
//
//        if (TextUtils.isEmpty(other_name)) {
//            ToastUtil.show("请选择其他联系人");
//            return;
//        }
//        if (TextUtils.isEmpty(other_phone)) {
//            ToastUtil.show("请选择其他联系人");
//            return;
//        }
//        if (TextUtils.isEmpty(other_relation)) {
//            ToastUtil.show("请选择其他联系人关系");
//            return;
//        }


////基本资料
//                basic_network_time //入网时长
//        basic_security_fund //社保公积金
//                basic_monthly_income //月收入
////紧急联系人
//        urgent_name //姓名
//                urgent_phone = //电话
//                urgent_relation = //关系
////其他联系人
//                        other_name //姓名
//        other_phone //电话
//                other_relation //关系
        OkGo.<JsonBean>post(HttpUrl.BASIC_INFO)
//                .params("basic_network_time", basic_network_time)
//                .params("basic_security_fund", basic_security_fund)
                .params("basic_monthly_income", basic_monthly_income)
                .params("basic_school", basic_school)

                .params("urgent_name", "")
                .params("urgent_phone", "")
                .params("urgent_relation", "")

                .params("other_name", "")
                .params("other_phone", "")
                .params("other_relation", "")
                .params("marriage", marriage)

                .params("occupation", occupation)
                .params("area", area)
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
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "BankInfo");
                                AppleActivityStepThree.forward(AppleActivityStepTwo.this);
                                finish();
                            }
                        }

                    }
                });
    }

    public void gotoNext(View v) {
        setData();
//        AppleActivityStepThree.forward(AppleActivityStepTwo.this);
    }


    /**
     *
     */
    private void showPickerView(final List<String> optionsItems, final TextView tv) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv.setText(optionsItems.get(options1) + "");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasAllGranted = true;
        int pos = 0;
        //判断是否拒绝  拒绝后要怎么处理 以及取消再次提示的处理
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                pos = i;
                break;
            }
        }
        if (hasAllGranted) { //同意权限做的处理,开启服务提交通讯录
//            showContacts();
//            showSms();
        } else {    //拒绝授权做的处理，弹出弹框提示用户授权
            dealwithPermiss(this, permissions[pos]);
        }
    }

    public void dealwithPermiss(final Activity context, final String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("操作提示")
                    .setCancelable(false)
                    .setMessage("注意：当前缺少必要权限！\n请点击“设置”-“权限”-打开所需权限")
                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isSetting = 1;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dealwithPermiss(AppleActivityStepTwo.this, permission);
                        }
                    })
                    .show();

        } else {
//            check();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // ContentProvider展示数据类似一个单个数据库表
            // ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver reContentResolverol = getContentResolver();
            // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
            Uri contactData = data.getData();
            // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            // 获得DATA表中的名字
            String username = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            // 条件为联系人ID
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone = reContentResolverol.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                            + contactId, null, null);
            while (phone.moveToNext()) {
                String usernumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (requestCode == 0) {
                    mTvUrgentName.setText(username + "");
                    mTvUrgentPhone.setText(usernumber + "");
                } else if (requestCode == 1) {
                    mTvOtherName.setText(username + "");
                    mTvOtherPhone.setText(usernumber + "");
                }
            }

        }
    }

    /**
     * 解析数据并组装成自己想要的list
     */
    private void parseData() {
        String jsonStr = new CityUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
//     数据解析
        List<JsonCityBean> shengList = new ArrayList<>();
//     把解析后的数据组装成想要的list
        shengList.addAll(JSON.parseArray(jsonStr, JsonCityBean.class));
//     遍历省
        for (int i = 0; i < shengList.size(); i++) {
            options1Items.add(shengList.get(i).name);
            //存放城市
            ArrayList<String> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
//         遍历市
            for (int c = 0; c < shengList.get(i).city.size(); c++) {
//        拿到城市名称
                String cityName = shengList.get(i).city.get(c).getName();
                cityList.add(cityName);

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).getArea() == null || shengList.get(i).city.get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).getArea());
                }
                province_AreaList.add(city_AreaList);
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

    }
}
