package com.cjqb.caijiqianbao;

import android.content.Intent;
import android.os.Bundle;

import com.cjqb.caijiqianbao.activity.BaseActivity;
import com.cjqb.caijiqianbao.activity.MainActivity;
import com.umeng.commonsdk.debug.I;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

/**
 * 启动页面
 * wmy
 * */
public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        startMainAct();
    }


    private void startMainAct(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    }
                });
            }
        } , 1500);
    }

}
