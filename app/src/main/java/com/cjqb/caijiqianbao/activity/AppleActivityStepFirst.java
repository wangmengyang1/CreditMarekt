package com.cjqb.caijiqianbao.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.bean.cityBean.JsonCityBean;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.ChineseCheck;
import com.cjqb.caijiqianbao.utils.CityUtil;
import com.cjqb.caijiqianbao.utils.DialogUtil;
import com.cjqb.caijiqianbao.utils.DpUtil;
import com.cjqb.caijiqianbao.utils.ScreenDimenUtil;
import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

public class AppleActivityStepFirst extends BaseActivity implements View.OnClickListener {
    //省
    private List<String> options1Items = new ArrayList<String>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TextView mTvAddress;
    private EditText mEdId;
    private EditText mEdName;
    private EditText mEdOp;
    private ImageView img;
    private View tv;
    private Dialog dialog;
    private String name;
    private String id_number;

    public static void forward(Context context) {
        Intent intent = new Intent(context, AppleActivityStepFirst.class);
//        intent.putExtra(Constants.PRODUCT_ID, product_id);
//        intent.putExtra(Constants.TO_NAME, title);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_1);
        mTvAddress = findViewById(R.id.tv_address);
        mEdId = findViewById(R.id.et_id);
        mEdName = findViewById(R.id.et_name);
        mEdOp = findViewById(R.id.et_op);
        findViewById(R.id.ll_sel_address).setOnClickListener(this);
        String stringValue = SpUtil.getInstance().getStringValue(SpUtil.VEST_TEMP);
        img = findViewById(R.id.img);
        tv = findViewById(R.id.tv);
        if ("1".equals(stringValue)){
//            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_step_1));
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_red_step_1));
            tv.setBackgroundResource(R.mipmap.ic_item_head);
        }else{
//            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_step_new_1));
            img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_blue_step_1));
            tv.setBackgroundResource(R.mipmap.ic_item_new_head);
        }

        img.setImageDrawable(getResources().getDrawable(R.mipmap.ic_new_red_step_1));
        setTitle("身份信息");
    }

    private void setData() {
        name = mEdName.getText().toString();
        id_number = mEdId.getText().toString();

        if (TextUtils.isEmpty(name)) {
            ToastUtil.show("请输入姓名");
            return;
        }
        if (!ChineseCheck.checkNameChese(name)) {
            ToastUtil.show("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(id_number)||id_number.length() != 18) {
            mEdId.setError("请输入身份证号");
            mEdId.requestFocus();
            return;
        }
//        String occupation = mEdOp.getText().toString();
//        String area = mTvAddress.getText().toString();
//        if (TextUtils.isEmpty(occupation)) {
//            mEdOp.setError("请输入您的职业");
//            mEdOp.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(area)) {
//            ToastUtil.show("请选择所在地");
//            return;
//        }

        showUpdata();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_sel_address) {
            //              解析数据
            parseData();
//              展示省市区选择器
            showPickerView();
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

    /**
     * 展示选择器
     */
    private void showPickerView() {
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

    public void gotoNext(View v) {
        setData();
//        AppleActivityStepTwo.forward(AppleActivityStepFirst.this);
    }

    public void showUpdata() {
        dialog = DialogUtil.commonDialog(this, R.layout.dialog_tip);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        TextView tv_name = dialog.findViewById(R.id.tv_name);
        TextView tv_idNumber = dialog.findViewById(R.id.tv_idNumber);
        tv_name.setText(name);
        tv_idNumber.setText(id_number);
        dialog.setCancelable(false);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (ScreenDimenUtil.getInstance().getScreenWdith()) - DpUtil.dp2px(this, 100);
        dialog.getWindow().setAttributes(p);
        dialog.show();

    }

    public void submit(View v) {
        if (dialog == null) return;
        dialog.cancel();
        dialog.dismiss();
        next();
    }

    public void next(){
        OkGo.<JsonBean>post(HttpUrl.AUTH_INFO)
                .params("name", name)
                .params("id_number", id_number)
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
                                SpUtil.getInstance().setStringValue(SpUtil.NAME, name);
                                SpUtil.getInstance().setStringValue(SpUtil.ID_NUMBER, id_number);
                                //身份信息BasicInfo    联系人信息   ContactInfo   银行信息  BankInfo   订单信息  OrderInfo
                                SpUtil.getInstance().setStringValue(SpUtil.STAGE, "ContactInfo");
//                                AppleActivityStepThree.forward(AppleActivityStepFirst.this);
                                AppleActivityStepTwo.forward(AppleActivityStepFirst.this);
                                finish();
                            }
                        }

                    }
                });
    }

    public void cancel(View v) {
        if (dialog == null) return;
        dialog.cancel();
        dialog.dismiss();
    }

}
