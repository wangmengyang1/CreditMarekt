package com.cjqb.caijiqianbao.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.cjqb.caijiqianbao.bean.vestBean.VestBean;
import com.cjqb.caijiqianbao.bean.vestBean.VestData;
import com.cjqb.caijiqianbao.glide.ImgLoader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.cjqb.caijiqianbao.R;
import com.cjqb.caijiqianbao.http.CommonCallback;
import com.cjqb.caijiqianbao.http.HttpUrl;
import com.cjqb.caijiqianbao.http.JsonBean;
import com.cjqb.caijiqianbao.utils.SpUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LauncherActivity extends BaseActivity {
    private ImageView mCover;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mCover = findViewById(R.id.cover);
//        ImgLoader.display(this, R.mipmap.screen, mCover);

    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.forward(LauncherActivity.this);
//                LoginActivity.forward(LauncherActivity.this);
//                HomeActivity.forward(LauncherActivity.this);
                finish();
            }
        }, 3000);
    }





}
