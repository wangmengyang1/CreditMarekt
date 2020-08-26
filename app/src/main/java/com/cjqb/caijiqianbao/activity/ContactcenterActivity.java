package com.cjqb.caijiqianbao.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjqb.caijiqianbao.Constants;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.adapter.ChanneAdapter;
import com.cjqb.caijiqianbao.bean.productListBean.ProductListJsonData;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

public class ContactcenterActivity extends BaseActivity {
    private ImageView img3;
    private ImageView img2;
    private ImageView img1;
    private TextView tv_time3;
    private TextView tv_time2;
    private TextView tv_time1;
    private TextView tv_content3;
    private TextView tv_content2;
    private TextView tv_content1;
    private String content;
    private String content1;
    private String content2;

    public static void forward(Context context) {
        Intent intent = new Intent(context, ContactcenterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        tv_content3 = findViewById(R.id.tv_content3);
        tv_content2 = findViewById(R.id.tv_content2);
        tv_content1 = findViewById(R.id.tv_content1);
        tv_time3 = findViewById(R.id.tv_time3);
        tv_time2 = findViewById(R.id.tv_time2);
        tv_time1 = findViewById(R.id.tv_time1);
        img3 = findViewById(R.id.img3);
        img2 = findViewById(R.id.img2);
        img1 = findViewById(R.id.img1);
        setTitle("联系客服");
        getList();
    }

    private void setType(String type, ImageView img, final TextView tv, final String content) {
        if ("phone".equals(type)) {
            img.setImageResource(R.mipmap.ic_phone);
            tv.setText("TEL:" + content);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = tv.getText().toString();
                    String replace = s.replace("TEL:", "");
                    callPhone(replace);
                }
            });
        }
        if ("qq".equals(type)) {
            img.setImageResource(R.mipmap.ic_qq);
            tv.setText("QQ号:" + content);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 将文本内容放到系统剪贴板里。
                    cm.setText(tv.getText().toString().replace("QQ号:", ""));
                    ToastUtil.show("复制成功!");
                }
            });

        }
        if ("email".equals(type)) {
            img.setImageResource(R.mipmap.ic_email);
            tv.setText("邮箱:" + content);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 将文本内容放到系统剪贴板里。
                    cm.setText(tv.getText().toString().replace("邮箱:", ""));
                    ToastUtil.show("复制成功!");
                }
            });
        }
    }

    private void getList() {
        OkGo.<JsonBean>get(HttpUrl.CONTACTCENTER)
                .execute(new CommonCallback<JsonBean>() {
                    @Override
                    public JsonBean convertResponse(okhttp3.Response response) throws Throwable {
                        return JSON.parseObject(response.body().string(), JsonBean.class);
                    }

                    @Override
                    public void onSuccess(Response<JsonBean> response) {
                        super.onSuccess(response);
                        JsonBean bean = response.body();
                        if (bean != null) {
                            if (("200").equals(bean.getCode())) {
                                JSONArray data = (JSONArray) bean.getData();
                                if (data != null && data.size() > 0) {
                                    JSONObject jSONObject0 = (JSONObject) data.get(0);
                                    String type = jSONObject0.getString("type");
                                    content = jSONObject0.getString("content");

                                    JSONObject jSONObject1 = (JSONObject) data.get(1);
                                    String type1 = jSONObject1.getString("type");
                                    content1 = jSONObject1.getString("content");

                                    JSONObject jSONObject2 = (JSONObject) data.get(2);
                                    String type3 = jSONObject2.getString("type");
                                    content2 = jSONObject2.getString("content");

                                    setType(type, img1, tv_content1, content);
                                    tv_time1.setText(jSONObject0.getString("title"));


                                    setType(type1, img2, tv_content2, content1);
                                    tv_time2.setText(jSONObject1.getString("title"));


                                    setType(type3, img3, tv_content3, content2);
                                    tv_time3.setText(jSONObject2.getString("title"));
                                }

                            }
                        }

                    }
                });
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
